/*
 * Copyright © 2014-2016 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.spark;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;

public class App   {


	public static void main(String[] args) {
		 SparkSession spark = SparkSession
			      .builder()
			      .appName("WordCountExample")
			      .getOrCreate();
		 try{
			 String sparkHome=System.getenv("SPARK_HOME");
			 String SAMPLEFILE =sparkHome +"/README.md";
		 
		    JavaRDD<String> lines = spark.read().textFile(SAMPLEFILE).javaRDD();
		 	    
			JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
				public Iterator<String> call(String s) {
					return Arrays.asList(s.split(" ")).iterator();
				}
			});
			JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
				public Tuple2<String, Integer> call(String s) {
					return new Tuple2<String, Integer>(s, 1);
				}
			});

			JavaPairRDD<String, Integer> counts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
				public Integer call(Integer a, Integer b) {
					return a + b;
				}
			});
		System.out.println("Total Words: "+ counts.count());
		 }catch(Exception e){
			 
		 }finally {
			 System.out.println("Job Finished");
		 }
	}
}
