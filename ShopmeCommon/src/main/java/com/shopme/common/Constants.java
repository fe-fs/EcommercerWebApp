package com.shopme.common;

//this class is used to connect the amazon AWS bucket (images-server) 
public class Constants {

	public static final String S3_BASE_URI;
	
	static {
		
		//using the system environment variables
		String bucketName = System.getenv("AWS_BUCKET_NAME");
		String region = System.getenv("AWS_REGION");
		String pattern = "https://%s.s3.%s.amazonaws.com";
		
		/*
		 * //this is just for test //String uri = String.format(pattern, bucketName,
		 * region); //System.out.println(uri);
		 * S3_BASE_URI = bucketName == null ? "" : uri; (comment the other one)
		 */		
		
		S3_BASE_URI = bucketName == null ? "" : String.format(pattern, bucketName, region);
		
	}
	/*
	 * //test running the application here to see the conection with database (if
	 * not appear restart IDE) public static void main(String[] args) {
	 * System.out.println("S3 BASE URI: " + S3_BASE_URI); }
	 */
}
