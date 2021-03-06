package br.com.ingenieux.mojo.beanstalk.env;

/*
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

import org.apache.maven.plugin.AbstractMojoExecutionException;
import org.jfrog.maven.annomojo.annotations.MojoGoal;
import org.jfrog.maven.annomojo.annotations.MojoParameter;
import org.jfrog.maven.annomojo.annotations.MojoSince;

import br.com.ingenieux.mojo.beanstalk.AbstractNeedsEnvironmentMojo;
import br.com.ingenieux.mojo.beanstalk.cmd.env.update.UpdateEnvironmentCommand;
import br.com.ingenieux.mojo.beanstalk.cmd.env.update.UpdateEnvironmentContext;
import br.com.ingenieux.mojo.beanstalk.cmd.env.update.UpdateEnvironmentContextBuilder;

import com.amazonaws.services.elasticbeanstalk.model.ConfigurationOptionSetting;

/**
 * Updates the environment versionLabel for a given environmentName
 * 
 * See the <a href=
 * "http://docs.amazonwebservices.com/elasticbeanstalk/latest/api/API_UpdateEnvironment.html"
 * >UpdateEnvironment API</a> call.
 * 
 */
@MojoGoal("update-environment")
@MojoSince("0.2.0")
public class UpdateEnvironmentMojo extends AbstractNeedsEnvironmentMojo {
	/**
	 * Version Label to use. Defaults to Project Version
	 */
	@MojoParameter(expression="${beanstalk.versionLabel}", defaultValue="${project.version}")
	String versionLabel;

	/**
	 * Application Description
	 */
	@MojoParameter(expression="${beanstalk.environmentDescription}")
	String environmentDescription;

	/**
	 * Configuration Option Settings
	 */
	@MojoParameter
	ConfigurationOptionSetting[] optionSettings;

	/**
	 * Template Name
	 */
	@MojoParameter(expression="${beanstalk.templateName}")
	String templateName;

	protected Object executeInternal() throws AbstractMojoExecutionException {
		UpdateEnvironmentContext context = UpdateEnvironmentContextBuilder
		    .updateEnvironmentContext().withEnvironmentId(environmentId)//
		    .withEnvironmentDescription(environmentDescription)//
		    .withEnvironmentName(environmentName)//
		    .withOptionSettings(optionSettings)//
		    .withTemplateName(templateName)//
		    .withVersionLabel(versionLabel)//
		    .build();
		UpdateEnvironmentCommand command = new UpdateEnvironmentCommand(this);

		return command.execute(context);
	}
}
