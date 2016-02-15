
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * This file has one Mapper and Reducer to parse Index file and move all spam and ham related data 
 * into  seperate folders. 
 * 
 */
public class FileUtilMapper {
	static Configuration conf = null;
	private static final Text EMPTY_TEXT = new Text("");	
	public static class SMapper extends Mapper<LongWritable, Text, Text, Text>{
		int x = 0;
		public void map(LongWritable key, Text value, Context output)  
	    		throws IOException, InterruptedException
	    {
			   Text classV = new Text();
			   Text path = new Text();
			 String[] strArr = value.toString().split(" ");
			 classV.set(strArr[0]);
			 path.set(strArr[1]);
			 output.write(classV,path );
	    }
		 
	}
	 
	public static class SpamReducer
	extends Reducer<Text,Text,Text,Text> {

		public void reduce(Text key, Iterable<Text> values, Context output)
	            throws IOException, InterruptedException
	        {
			
			Configuration cfg = new Configuration();
			FileSystem fs = FileSystem.get(cfg);
			String s = "";
			    for (Text val : values) 
			    {
			    	Path srcPath = new Path("hdfs://cshadoop1/vxa141230/final/spam_data/"+val.toString()); 
			    	Path destPath = new Path("hdfs://cshadoop1/vxa141230/final/spam_data/"+key.toString()+"/"+val.toString());
			    	
			    	boolean succ = FileUtil.copy(fs,srcPath,fs,destPath,false,cfg);
			    	s = s+key.toString()+"/"+val.toString()+": "+srcPath+" "+destPath+" "+succ+"!";
			    
			    }
			   
			    
			    output.write(new Text(key.toString()), new Text(s));
			 }
	}

	// Driver program
		public static void main(String[] args) throws Exception { 
			
			Configuration conf = new Configuration();
			Job job = new Job(conf);
			job.setJobName("spamJob");
			job.setJarByClass(FileUtilMapper.class);
			
			job.setMapperClass(SMapper.class);
			job.setReducerClass(SpamReducer.class);

			// set output key type 
			job.setOutputKeyClass(Text.class);
			// set output value type 
			job.setOutputValueClass(Text.class);
			//set the HDFS path of the input data
			FileInputFormat.addInputPath(job, new Path(args[0]));
			// set the HDFS path for the output
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			//Wait till job completion
			job.waitForCompletion(true);
			MainClass mainObj = new MainClass();
			
			mainObj.main(args);
			
		 }
			
		}
