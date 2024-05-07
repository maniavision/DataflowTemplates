/*
 * Copyright (C) 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.cloud.teleport.v2.gcp;

import com.google.cloud.secretmanager.v1.ListSecretsRequest;
import com.google.cloud.secretmanager.v1.ProjectName;
import com.google.cloud.secretmanager.v1.Secret;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;

public class SecretManager {

  public String getSecret(String projectId, String secretId) throws Exception {
    try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
      // Build the parent name from the project.
      ProjectName projectName = ProjectName.of(projectId);
      //            SecretVersionName.of()

      ListSecretsRequest request =
          ListSecretsRequest.newBuilder()
              .setParent(projectName.toString())
              .setFilter("mongo_url_auth")
              .build();
      SecretManagerServiceClient.ListSecretsPagedResponse pagedResponse =
          client.listSecrets(request);

      Secret secret = pagedResponse.iterateAll().iterator().next();
      return secret.getName();
    }
  }
}
