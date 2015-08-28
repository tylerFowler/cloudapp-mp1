# cloudapp-mp1
Machine Programming Assignment for Cloud Application Course

## Instructions (from PDF):
1. Divide each sentence into a list of words provided in the **"delimiters"** variable
2. Make all the tokens lowercase and remove any tailing and leading spaces
3. Ignore all common words provided in the **"stopWordsArray"** variable
4. Keep track of word frequencies
5. Sort the list by frequency in a descending order. If two words have the same number
count, use the lexigraphy. For example, the following is a sorted list:
  - `({Orange, 3), (Apple, 2), (Banana, 2)}`
6. Return the top 20 items from the sorted list as a String Array

*Note:*

To make the application more interesting, you have to process only the titles with certain
indexes. These indexes are accessible using the “getIndexes” method, which returns an Integer
Array with 0-based indexes to the input file. It is possible to have an index appear several times.
In this case, just process the index multiple times.

#### Sample list given user id of `0`
```log
list
de
state
school
disambiguation
county
new
john
album
c
river
station
united
highway
national
saint
william
route
f
film
```
