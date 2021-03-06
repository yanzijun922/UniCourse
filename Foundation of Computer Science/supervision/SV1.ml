(*1. Some people believe that bad luck will befall them if the day of the week is a Friday and the day of the month is the 13th.  Is it more likely than 1-in-7 that the 13th day of any month is a Friday?

2. In a typical year, which day of the week occurs most, or are all 7 days equally common?
*)
(*SOME NOTES AND QURETIONS
Day of the week is represent by Sunday is 0 Monday is 1 ..etc
and I dont quite understand the quetion2 because 365 mod 7 = 2 and 366 mod 7 = 3
so there must be 2 or 3 days of week to be more common?? My answer is base on this..
Do I misunderstanded the question?
*)
fun isLunar y = y mod 400 = 0 orelse (y mod 100 <> 0 andalso y mod 4 = 0)
fun dayInYear y = (if (isLunar y) then 366 else 365)
fun dayInMonth (m,y) = 
    case m of
        1 => 31
        |2 => 28 + (if (isLunar y) then 1 else 0)
        |3 => 31
        |4 => 30
        |5 => 31
        |6 => 30
        |7 => 31
        |8 => 31
        |9 => 30
        |10=> 31
        |11=> 30
        |12=> 31
        |_ => 0

(*I call the 1st day of the month got the same day of week a month with the same pattern
So acturally I am looking for a the month with Firday on 13th etc...
and January of 2017 satisfy this condition, so the calculation is based on 2017
*)
fun repeatMonth(mm,yy,endy) = 
    let 
        fun f (m,y,n,d1,d2) = if y = endy then d1 / (d1+d2)
                        else if n mod 7 = 0 andalso m <> 12 then f (m+1,y,dayInMonth (m,y),d1+1.0,d2)
                        else if n mod 7 = 0 andalso m = 12 then f (1,y+1,dayInMonth (m,y),d1+1.0,d2)
                        else if n mod 7 <>0 andalso m <>12 then f (m+1,y,n+dayInMonth (m,y),d1,d2+1.0)
                        else if n mod 7 <>0 andalso m = 12 then f (1,y+1,n+dayInMonth (m,y),d1,d2+1.0)
                        else 0.0
    in
        f (mm,yy,0,0.0,0.0)
    end;
val ans1 = repeatMonth(1,2017,1000000);

(*The calandar seems to be changed on the histroy, i choose 2017 to 
ensure the calculation the calendar we are using today..*)

fun firstDayOfYear yy = 
    let 
        val y0 = 2017
        and d0 = 0
        fun daydif y = if y = y0 then 0
                        else if y < y0 then ~(dayInYear y) + (daydif (y+1)) 
                        else (dayInYear (y-1)) + (daydif (y-1))
        fun res y = (d0 + (daydif y)) mod 7

    in
        res yy
    end;
    
fun extraDayOfYear yy = 
    let 
        val t = firstDayOfYear yy
    in
        if (isLunar yy) then [t,(t+1) mod 7,(t+2) mod 7]
        else [t,(t+1) mod 7] 
    end;

