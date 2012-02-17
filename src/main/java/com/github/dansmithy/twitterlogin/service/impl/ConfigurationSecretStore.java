package com.github.dansmithy.twitterlogin.service.impl;

import java.io.File;
import java.io.IOException;

import javax.inject.Named;

import org.apache.commons.io.FileUtils;

import com.github.dansmithy.twitterlogin.service.SecretStore;
import com.google.common.base.Objects;

@Named
public class ConfigurationSecretStore implements SecretStore {

	private static final String ENVIRONMENT_PROEPRTY = "twitter_consumer_key";
	
	/* (non-Javadoc)
	 * @see com.github.dansmithy.twitterlogin.service.impl.SecretStore#getConsumerKey()
	 */
	@Override
	public String getConsumerKey() {
		String environmentValue = System.getenv(ENVIRONMENT_PROEPRTY);
		String filesystemValue = getFilesystemValue();
		try {
			return Objects.firstNonNull(environmentValue, filesystemValue);
		} catch (NullPointerException e) {
			throw new RuntimeException("Cannot read twitter consumer key either from system property or from file", e);
		}
	}
	
	private String getFilesystemValue() {
		String userDir = System.getProperty("user.home");
		String userFile = userDir + "/twitter.consumer.key";
		try {
			return FileUtils.readFileToString(new File(userFile));
		} catch (IOException e) {
			return null;
		}
	}

}
