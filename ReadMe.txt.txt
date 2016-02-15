ReadMe Instructions: 
Team Members :
Venkata Vinay Kumar Marpina – vxm133430
Venkatesh Avula – vxa141230
Vijay Krishn Vantipalli – vxv140430 
Karthik Velaga - kxv140730


i.	Spam Classification

1. Create Runnable Jars from the following java files. 
FileUtilMapper.java ==> FileUtilMapper.jar
SpamMapper.java	==> SpamMapper.jar

2. Copy above jars into cluster folder location.

3. Copy input index file and data into following folders. 
   /vxa141230/final/spam_data/data/
4. Create 2 folders on hadoop clusters for spam and ham folders respectively.
	/vxa141230/final/spam_data/ham/data/
	/vxa141230/final/spam_data/spam/data/
	
5. Run Map Reduce on hadoop cluster with following commands.
	hadoop jar FileUtilMapper.jar /vxa141230/index_001.txt /vxa141230/spam_output4
	hadoop jar SpamMapper.jar /vxa141230/final/spam_data/spam/data/001 /vxa141230/ham_001_op2
6. Now spam and ham folders are having respective datas and comma seperated trained vocabulary is in following loactions.Copy them to local to run java modules.
	hdfs dfs -copyToLocal /vxa141230/ham_001_op1
7. ===============================================================================
	Running Naive Based 
   ===============================================================================
	java MainClass F:/MS/classes/Sem-3/BigData/workspace/VXA_AS4/spam_output F:/MS/classes/Sem-3/BigData/workspace/VXA_AS4/ham_output F:/MS/classes/Sem-3/BigData/workspace/VXA_AS4/spam F:/MS/classes/Sem-3/BigData/workspace/VXA_AS4/ham NB
8.  ===============================================================================
	Running Logistic Regression 
   ===============================================================================
	java MainClass F:/MS/classes/Sem-3/BigData/workspace/VXA_AS4/spam_output F:/MS/classes/Sem-3/BigData/workspace/VXA_AS4/ham_output F:/MS/classes/Sem-3/BigData/workspace/VXA_AS4/spam F:/MS/classes/Sem-3/BigData/workspace/VXA_AS4/ham LR 10 0.01 0.5
 

 
ii. Movie Ratings Recommendations:

1.  Run the following command by placing tained data in specific location.Output file will be created here which will be input to the pig.Copy this to cluster where pig is available 
 java CollaborativeFilter <path to trainfile.txt> <path to testfile.txt>
 
2. Run pig file with this command. This will give final output
	pig -x local mv_ratings.pig
	 
	 