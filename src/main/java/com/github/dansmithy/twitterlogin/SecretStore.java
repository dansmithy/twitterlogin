package com.github.dansmithy.twitterlogin;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Objects;

public class SecretStore {

	private static final String ENVIRONMENT_PROEPRTY = "twitter_consumer_key";
	
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

	public static void main(String[] args) {
		System.out.println(new SecretStore().getConsumerKey());
	}
	
	
}
