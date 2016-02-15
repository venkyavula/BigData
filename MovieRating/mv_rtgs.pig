		A = LOAD 'predictedRatings.txt' USING PigStorage(',');
        B = foreach A generate (int) $0 as movieId, (int) $1 as userId, (float) $2  as rating;
        C = GROUP B BY movieId ;
        D = FOREACH C GENERATE group, AVG(B.rating) as rtg ;
		D2 = FILTER D BY rtg >= 3.5;
        E = LOAD 'movie_titles.txt' USING PigStorage(',');
		F = foreach E generate (int) $0 as movieId, (chararray) $1 as dateM, (chararray) $2  as movieTitle;
		G = JOIN D2 BY $0, F BY $0 ;
		H = foreach G generate $4 , $1 ;
		DUMP H;

