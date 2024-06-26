/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.execution;

import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Project component that keeps track of {@link RunConfigurationProducer} implementations that should be ignored for a given project. All
 * subclasses of classes specified here will be ignored when looking for configuration producers.
 */
@Service(Service.Level.PROJECT)
@State(name = "RunConfigurationProducerService", storages = @Storage("runConfigurations.xml"))
public final class RunConfigurationProducerService implements PersistentStateComponent<RunConfigurationProducerService.State> {
  private State myState = new State();

  @Override
  public @NotNull State getState() {
    return myState;
  }

  @Override
  public void loadState(@NotNull State state) {
    myState = state;
  }

  public static final class State {
    public Set<String> ignoredProducers = new HashSet<>();
  }

  public static @NotNull RunConfigurationProducerService getInstance(@NotNull Project project) {
    return project.getService(RunConfigurationProducerService.class);
  }

  public void addIgnoredProducer(@NotNull Class<? extends RunConfigurationProducer<?>> ignoredProducer) {
    myState.ignoredProducers.add(ignoredProducer.getName());
  }

  public boolean isIgnored(RunConfigurationProducer<?> producer) {
    return myState.ignoredProducers.contains(producer.getClass().getName());
  }
}
