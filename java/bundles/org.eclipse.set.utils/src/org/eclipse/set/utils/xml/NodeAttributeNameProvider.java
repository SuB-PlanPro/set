/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Determines an attribute/group name for an XML node
 * 
 * @author Stuecker
 *
 */
public class NodeAttributeNameProvider {
	private static final String TEXT_NODE = "#text"; //$NON-NLS-1$
	private static final String DOCUMENT_NODE = "#document"; //$NON-NLS-1$
	private static final String CONTAINER_NODE = "Container"; //$NON-NLS-1$

	private static final String IDENTITY_ATTRIBUTE_NAME = "Identitaet"; //$NON-NLS-1$
	private static final String VALUE_ATTRIBUTE_NAME = "Wert"; //$NON-NLS-1$

	/**
	 * @param node
	 *            The node to find the attribute name
	 * @return The closest XML attribute name (or null)
	 */
	public String getAttributeName(final Node node) {
		// When the parent node is container node or root node, then the node
		// isn't
		// attribute/group node
		if (node == null || node.getParentNode() == null
				|| node.getParentNode().getNodeName().equals(DOCUMENT_NODE)
				|| node.getParentNode().getNodeName().equals(CONTAINER_NODE)) {
			return null;
		}

		final Node parentNode = node.getParentNode();

		if (node.getNodeName().equals(TEXT_NODE)) {
			return getAttributeName(parentNode);
		}

		// If this contains a value subelement, return it
		// Also return it if the parent object is a LST-Object
		if (isValueType(node) || isObjectType(parentNode)) {
			return node.getNodeName();
		}

		return parentNode.getNodeName();
	}

	private static boolean isObjectType(final Node node) {
		return hasChildOfType(node,
				NodeAttributeNameProvider.IDENTITY_ATTRIBUTE_NAME);
	}

	private static boolean isValueType(final Node node) {
		return hasChildOfType(node,
				NodeAttributeNameProvider.VALUE_ATTRIBUTE_NAME);
	}

	private static boolean hasChildOfType(final Node node, final String name) {
		final NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			final Node child = children.item(i);
			if (child.getNodeName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
