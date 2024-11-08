# Group Splitter

Groups people together by trying to group people who haven't been grouped together much.

## data file format

```
3
PersonA	 	2	3	2	1	0	2	0
PersonB	2	 	3	1	2	0	2	0
PersonC	3	3	 	2	2	0	1	0
PersonD 2	1	2	 	3	0	1	0
PersonE 1	2	2	3	 	0	2	0
PersonF 0	0	0	0	0	 	0	0
x PersonG 2	2	1	1	2	0	 	0
PersonH 0	0	0	0	0	0	0

PersonA PersonB
```

The first line is a single number with the number of desired groups, *M*.
The next *X* lines list the data for how many times people have been grouped together. Each line is preceded by an optional "x". Then the name of a person without any spaces. Then *X-1* space delimited integers that record the number of times this person has been grouped with another person. The diagonal is automatically skipped so no number is required there.

> E.g. if *X* = 3, then at the 2nd person, the first number records the number of times he has been paired with person 1, the next number records the number of time he has been paired with person 3. There's no number for how many times person 2 has been paired with himself.

If a person's name has an "x" before it followed by a space, this person will not be sorted into a group later on. This is like a marker for "absent".

The *X* lines are terminated by an empty line.

Following the empty line, there can be up to *M* lines. Each *i*th line lists people that are presorted into group *i*. In the example above, PersonA and PersonB will be put into group 1 and the algorithm will not try sorting them. Use this if you want certain people to be put into specific groups but let everyone else still be sorted.