# ProxxBlackHoles
ProxxBlackHoles is a console game with the rules similar to https://proxx.app/.

Example of console output when holes are visible for debug purposes with the following board: with 33, heigth 10 and number of holes 21:

~~~Welcome to the Black Hole console game.
  012345678901234567890123456789012
0 .................................
1 ..!...................!.....!....
2 .................................
3 .........!...........!...........
4 .!.!.........!.....!.............
5 ....!.............!..............
6 ..!.........!...................!
7 .!.....................!......!.!
8 ....................!.......!....
9 ........................!........
Type zero-based x and y and press enter, for example 5 7
>10 0
>  012345678901234567890123456789012
0 ...1                 1...........
1 ..!1                 1!.....!....
2 ...1    111         12...........
3 ...11   1!1 111   112!...........
4 .!.!21  111 1!1  12!.............
5 ....!1     1221  1!..............
6 ..!211     1!1   111............!
7 .!21       111     1...!......!.!
8 111                1!.......!....
9                    1....!........

(removed 272 lines)

Type zero-based x and y and press enter, for example 5 7
>22 0
>  012345678901234567890123456789012
0  111                 111   1.1   
1  1!1                 1!1   1!1   
2  111    111         1221   111   
3 11211   1!1 111   112!1          
4 1!2!21  111 1!1  12!211          
5 1233!1     1221  1!21          11
6 12!211     1!1   111  111    113!
7 1!21       111     1111!1  112!3!
8 111                1!11221 1!2121
9                    111 1!1 111   
Type zero-based x and y and press enter, for example 5 7
>28 0
>You have won. All cells are opened.
  012345678901234567890123456789012
0  111                 111   111   
1  1!1                 1!1   1!1   
2  111    111         1221   111   
3 11211   1!1 111   112!1          
4 1!2!21  111 1!1  12!211          
5 1233!1     1221  1!21          11
6 12!211     1!1   111  111    113!
7 1!21       111     1111!1  112!3!
8 111                1!11221 1!2121
9                    111 1!1 111   
Thank you for using the Black Hole console game.
~~~
