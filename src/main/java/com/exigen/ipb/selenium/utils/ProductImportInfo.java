package com.exigen.ipb.selenium.utils;

/**
 * A class that is used to hold product import information: product code, version
 * and path to its archive.
 * 
 * @author gzukas
 * @since 3.9
 */
public class ProductImportInfo {

	public static final String DEFAULT_PRODUCT_DIR = "target/test-classes/products";

	private static final String DEFAULT_PRODUCT_PATH_FORMAT = DEFAULT_PRODUCT_DIR + "/%s.zip";

	private String productCd;

	private double productVersion;

	private String productPath;
	
	public ProductImportInfo(String productCd) {
		this(productCd, 1.0);
	}
	
	public ProductImportInfo(String productCd, double productVersion) {
		this(productCd, productVersion, String.format(DEFAULT_PRODUCT_PATH_FORMAT, productCd));
	}
	
	public ProductImportInfo(String productCd, double productVersion, String productPath) {
		this.productCd = productCd;
		this.productVersion = productVersion;
		this.productPath = productPath;
	}
	
	public String getProductCd() {
		return productCd;
	}
	
	public double getProductVersion() {
		return productVersion;
	}
	
	public String getProductPath() {
		return productPath;
	}
}
