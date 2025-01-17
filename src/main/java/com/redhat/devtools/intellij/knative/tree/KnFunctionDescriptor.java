/*******************************************************************************
 * Copyright (c) 2021 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc.
 ******************************************************************************/
package com.redhat.devtools.intellij.knative.tree;

import com.google.common.base.Strings;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.ide.util.treeView.PresentableNodeDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.redhat.devtools.intellij.knative.kn.Function;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KnFunctionDescriptor extends PresentableNodeDescriptor<IKnFunctionNode> {
    private static final Icon SERVICE_ICON = IconLoader.findIcon("/images/service.svg");
    private final IKnFunctionNode node;

    protected KnFunctionDescriptor(Project project, IKnFunctionNode node, @Nullable NodeDescriptor parentDescriptor) {
        super(project, parentDescriptor);
        this.node = node;
    }

    @Override
    protected void update(@NotNull PresentationData presentation) {
        Function function = node.getFunction();
        presentation.setPresentableText(function.getName());
        presentation.setLocationString(getLabel(function));
        presentation.setIcon(SERVICE_ICON);
        if (!function.getLocalPath().isEmpty()) {
            presentation.setTooltip("Name: " + function.getName() + "\n" +
                    (Strings.isNullOrEmpty(function.getNamespace()) ? "" : "Namespace: " + function.getNamespace() + "\n") +
                    "Runtime: " + function.getRuntime() + "\n" +
                    "Context: " + function.getLocalPath()
            );
        }
    }

    private String getLabel(Function function) {
        boolean isLocal = !Strings.isNullOrEmpty(function.getLocalPath());
        if (function.isPushed() && isLocal) {
            return "local/cluster";
        } else if (isLocal) {
            return "local only";
        } else {
            return "cluster only";
        }
    }

    @Override
    public IKnFunctionNode getElement() {
        return node;
    }
}
