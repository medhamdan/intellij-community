// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.ide.passwordSafe;

import com.intellij.credentialStore.CredentialStore;
import com.intellij.credentialStore.Credentials;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.credentialStore.CredentialAttributesKt.CredentialAttributes;

public interface PasswordStorage extends CredentialStore {
  @Deprecated
  default void setPassword(@NotNull Class<?> requestor, @NotNull String accountName, @Nullable String value) {
    set(CredentialAttributes(requestor, accountName), value == null ? null : new Credentials(accountName, value));
  }

  /**
   * @deprecated Please use {@link #setPassword} and pass value as null
   */
  @SuppressWarnings("unused")
  @Deprecated
  default void removePassword(@SuppressWarnings("UnusedParameters") @Nullable Project project, @NotNull Class requestor, String key) {
    //noinspection deprecation
    setPassword(requestor, key, null);
  }

  /**
   * @deprecated Please use {@link #setPassword}
   */
  @Deprecated
  default void storePassword(@SuppressWarnings("UnusedParameters") @Nullable Project project, @NotNull Class requestor, @NotNull String key, @Nullable String value) {
    //noinspection deprecation
    setPassword(requestor, key, value);
  }

  @Deprecated
  @Nullable
  default String getPassword(@SuppressWarnings("UnusedParameters") @Nullable Project project, @NotNull Class requestor, @NotNull String key) {
    //noinspection deprecation
    return getPassword(CredentialAttributes(requestor, key));
  }
}
