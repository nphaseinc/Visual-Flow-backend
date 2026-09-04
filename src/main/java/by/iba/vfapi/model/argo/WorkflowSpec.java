/*
 * Copyright (c) 2021 IBA Group, a.s. All rights reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package by.iba.vfapi.model.argo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class represents spec for custom resource Workflow.
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowSpec implements Serializable {
    private static final long serialVersionUID = 1;

    private WorkflowTemplateRef workflowTemplateRef;
    private String entrypoint;
    private String onExit;
    private List<ImagePullSecret> imagePullSecrets;
    private List<Template> templates;
    private String serviceAccountName;
    private boolean suspend;
    private String shutdown;

    /**
     * Build a WorkflowSpec by copying runnable fields from a WorkflowTemplateSpec.
     * Avoids workflowTemplateRef lookups that can fail when the controller informer
     * has not yet observed a newly created WorkflowTemplate.
     *
     * @param templateSpec source WorkflowTemplateSpec
     * @return WorkflowSpec with inlined templates
     */
    public static WorkflowSpec fromWorkflowTemplateSpec(WorkflowTemplateSpec templateSpec) {
        if (templateSpec == null) {
            throw new IllegalArgumentException("WorkflowTemplateSpec must not be null");
        }
        return new WorkflowSpec()
                .entrypoint(templateSpec.getEntrypoint())
                .onExit(templateSpec.getOnExit())
                .templates(templateSpec.getTemplates())
                .serviceAccountName(templateSpec.getServiceAccountName())
                .imagePullSecrets(templateSpec.getImagePullSecrets());
    }

    /**
     * Setter for serviceAccountName.
     *
     * @param serviceAccountName serviceAccountName object
     * @return this
     */
    public WorkflowSpec serviceAccountName(String serviceAccountName) {
        this.serviceAccountName = serviceAccountName;
        return this;
    }

    /**
     * Setter for workflowTemplateRef.
     *
     * @param workflowTemplateRef WorkflowTemplateRef object
     * @return this
     */
    public WorkflowSpec workflowTemplateRef(WorkflowTemplateRef workflowTemplateRef) {
        this.workflowTemplateRef = workflowTemplateRef;
        return this;
    }

    /**
     * Setter for entrypoint.
     *
     * @param entrypoint entrypoint
     * @return this
     */
    public WorkflowSpec entrypoint(String entrypoint) {
        this.entrypoint = entrypoint;
        return this;
    }

    /**
     * Setter for onExit.
     *
     * @param onExit onExit template name
     * @return this
     */
    public WorkflowSpec onExit(String onExit) {
        this.onExit = onExit;
        return this;
    }

    /**
     * Setter for templates.
     *
     * @param templates list of Template
     * @return this
     */
    public WorkflowSpec templates(Collection<Template> templates) {
        if (templates != null) {
            this.templates = new ArrayList<>(templates);
        }
        return this;
    }

    /**
     * Setter for imagePullSecrets.
     *
     * @param imagePullSecrets list of ImagePullSecret
     * @return this
     */
    public WorkflowSpec imagePullSecrets(Collection<ImagePullSecret> imagePullSecrets) {
        if (imagePullSecrets != null) {
            this.imagePullSecrets = new ArrayList<>(imagePullSecrets);
        }
        return this;
    }
}