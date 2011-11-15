package com.exigen.ipb.selenium.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.exigen.ipb.e2e.support.data.EnvironmentRepresentation;
import com.exigen.ipb.e2e.support.rs.util.RSWebClientBuilder;
import org.apache.cxf.jaxrs.client.WebClient;
/**
 * Provides ability to manage product imports through RESTful web service.
 *
 * @author gzukas
 * @since 3.9
 */
public class RestfulProductManager {
	
	public static final String SERVICE_PRODUCT_PATH = "/services/PfRestful/productServices/product/";
		
	private EnvironmentRepresentation env;
	
	public RestfulProductManager(EnvironmentRepresentation env) {
		this.env = env;
	}
	
	/**
	 * Imports product using RESTful service.
	 * 
	 * @see #importProduct(java.io.File)
	 * 
	 * @param productFileName  The name of product file.
	 * @return  Response of web client.
	 */
	public Response importProduct(String productFileName) {
		return importProduct(new File(productFileName));
	}
    
	public Response importProduct(InputStream stream, boolean replaceUserComponents) {
		WebClient webClient = RSWebClientBuilder.createWebClient(env, env.getAdminApplicationUrl());
		Response response = webClient.path(SERVICE_PRODUCT_PATH + Boolean.toString(replaceUserComponents)).post(stream);
		
		validateResponseStatus(response, Status.CREATED,
				"Could not import product. Maybe it already exists? " +
				"Response code: " + response.getStatus());
		
		return response;		
	}
	
	/**
	 * Imports product using RESTful web service.
	 * 
	 * @param productFile  Product file in zip format.
	 * @return  Response of web client.
	 */
	public Response importProduct(File productFile, boolean replaceUserComponents) {
		validateMimeType(productFile, MediaType.APPLICATION_OCTET_STREAM);
		InputStream stream = null;
		try {
			stream = new FileInputStream(productFile);
		}
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
		
		return importProduct(stream, replaceUserComponents);
	}
	
	public Response importProduct(File productFile) {
		return importProduct(productFile, false);
	}

	public void deployAndActivate(String productCode, double productVersion) {
		WebClient webClient = RSWebClientBuilder.createWebClient(env, env.getAdminApplicationUrl());
		webClient.path(SERVICE_PRODUCT_PATH + "deployAndActivate/" + productCode + "/" + Double.toString(productVersion)).post(null);
	}
	
	/**
	 * Returns product definition.
	 * 
	 * @param productCd  Product code.
	 * @param version  Product version.
	 * @param clazz  Class of product definition.
	 * @return  Product definition.
	 */
	public <T> T findProduct(String productCd, double version, Class<T> clazz) {
		return RSWebClientBuilder.createWebClient(env, env.getAdminApplicationUrl())
				.path(formatProductPath(productCd, version))
				.accept(MediaType.APPLICATION_XML)
				.get(clazz);
	}
	
	/**
	 * Deletes existing product.
	 * 
	 * @param productCd  Product code.
	 * @param version  Product version.
	 * @return  Response of web client.
	 */
	public Response deleteProduct(String productCd, double version) {
		return RSWebClientBuilder.createWebClient(env, env.getAdminApplicationUrl())
				.path(formatProductPath(productCd, version))
				.delete();
	}
	
	/**
	 * Verifies content type of given file.
	 * 
	 * @param file  The file to be verified.
	 * @param mime  Expected content type.
	 * @throws IllegalArgumentException  If content types do not match.
	 */
	private void validateMimeType(File file, String mime) {
		if (!new MimetypesFileTypeMap().getContentType(file).equals(mime)) {
			throw new IllegalArgumentException("File should be type of " + mime);
		}
	}
	
	/**
	 * Verifies response status.
	 * 
	 * @param response  Response instance.
	 * @param status  Expected status.
	 * @param errorMessage  Message to be shown in case of failure.
	 * @throws IllegalStateException  If expected and actual statuses do not match.
	 */
	private void validateResponseStatus(Response response, Status status, String errorMessage) {
		if (response.getStatus() != status.getStatusCode()) {
			throw new IllegalStateException(errorMessage);
		}
	}
	
	/**
	 * Formats relative path to product.
	 * 
	 * @param productCd  Product code.
	 * @param version  Product version.
	 * @return  Relative path to product.
	 */
	private String formatProductPath(String productCd, double version) {
		return new StringBuilder(SERVICE_PRODUCT_PATH)
			.append(productCd).append("/").append(version)
			.toString();
	}
	
}
