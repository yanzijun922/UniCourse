fun isMember (x,[]) = false
  | isMember (x,l::ls) = x = l orelse isMember (x,ls);
(*fun filter (fn,[]) = []
  | filter (fn,l::ls) = if (fn l) then l :: filter (fn,ls)
fun check (x::xs,y::ys) = filter (fn z => isMember(z,y::ys)) (x::xs);*)
fun inter ([],_) = []
  | inter (x::xs,y::ys) = if isMember(x,y::ys) then x::inter (xs,y::ys)
                            else inter (xs,y::ys);

fun sort [] = []
  | sort (x1::[]) = [x1]
  | sort (x1::(x2::xs)) = if x1 < x2  then x1 :: sort(x2::xs)
                        else x2 :: sort(x1::xs);
fun bubsort(xs,0) = xs 
  | bubsort(x::xs,n) = bubsort(sort(x::xs),n-1);

fun revbubble [] = []
  | revbubble(xs) = 
    let
        val li = sort(xs)
    in  
        (hd li) :: revbubble (tl li)
    end;

fun take (_,0) = []
  | take (x::xs,n) = x :: take (xs,n-1);

fun drop (xs,0) = xs
  | drop (x::xs,n) = drop (xs,n-1); 

fun mergeList(xs,[]) = xs
  | mergeList([],ys) = ys 
  | mergeList(x::xs,y::ys) = if x < y then x :: mergeList(xs,y::ys)
                            else y :: mergeList(x::xs,ys);



fun mergeSort [] = []
  | mergeSort (x::[]) = [x]
  | mergeSort (xs) = mergeList (mergeSort(take(xs,(length xs) div 2)),mergeSort(drop(xs,(length xs) div 2)));

datatype 'a tree = LF
                  |BR of 'a * 'a tree * 'a tree;

fun insertEle (x,LF) = BR(x,LF,LF)
  | insertEle (x,BR(a,b,c)) = if x < a then BR(a,insertEle (x,b),c) 
                              else BR(a,b,insertEle (x,c));

fun buildTree [] = LF
  | buildTree (l::ls) = insertEle (l,buildTree ls);

fun filterTree (LF,ls) = []
  | filterTree (BR(a,br1,br2),ls) = 
    if isMember (a,ls) 
    then a :: (filterTree (br1,ls) @ filterTree (br2,ls))
    else filterTree (br1,ls) @ filterTree (br2,ls)
