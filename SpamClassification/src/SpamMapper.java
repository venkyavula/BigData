
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * This Mapper file will remove unwanted data from the trained data (spam and ham) by removing 
 * special characters and preparing the vocab file each word is separated by comma
 *
 */

public class SpamMapper {
	private static final Text EMPTY_TEXT = new Text("");
	private static final String homeDir = "hdfs://cshadoop1/";
	private static String outputFolder ="";
	public static class SMapper extends Mapper<LongWritable, Text, NullWritable, Text>{
		int x = 0;
		public void map(LongWritable key, Text value, Context output)  
	    		throws IOException, InterruptedException
	    {
			 Text rating = new Text();
			 String valueModified = value.toString().replaceAll("[^a-zA-Z]", " ");
			 if(valueModified.trim().length()!=0)
			 {
				 valueModified = valueModified.replaceAll("\\s+",",");
				 rating.set(valueModified);
				 output.write(NullWritable.get(),rating );
			 }
	    }
		 
	}
 
	 
	// Driver program
		public static void main(String[] args) throws Exception { 
			outputFolder = args[1];
			Configuration conf = new Configuration();
			Job job = new Job(conf);
			job.setJobName("spamJob");
			job.setJarByClass(SpamMapper.class);
			
			job.setMapperClass(SMapper.class);

			// set output key type 
			job.setOutputKeyClass(NullWritable.class);
			// set output value type 
			job.setOutputValueClass(Text.class);
			//set the HDFS path of the input data
			FileInputFormat.addInputPath(job, new Path(args[0]));
			// set the HDFS path for the output
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			//Wait till job completion
			job.waitForCompletion(true);
			
			
			
		 }
			
		}
