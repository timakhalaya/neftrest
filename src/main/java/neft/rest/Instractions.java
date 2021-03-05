/*
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package neft.rest;

import org.tempuri.Add;
import org.tempuri.Divide;
import org.tempuri.Multiply;
import org.tempuri.Subtract;

import com.fasterxml.jackson.annotation.JsonProperty;

final class Instruction {

  @JsonProperty
  Add add;

  @JsonProperty
  Divide divide;

  @JsonProperty
  Multiply multiply;

  @JsonProperty
  Subtract subtract;

  Object operation() {
    if (add != null) {
      return add;
    } else if (divide != null) {
      return divide;
    } else if (multiply != null) {
      return multiply;
    } else {
      return subtract;
    }
  }

  String soapAction() {
    return "\"http://tempuri.org/" + operationName() + "\"";
  }

  private String operationName() {
    if (add != null) {
      return "Add";
    } else if (divide != null) {
      return "Divide";
    } else if (multiply != null) {
      return "Multiply";
    } else {
      return "Subtract";
    }
  }
}
