### Answers Score: 17/20
- Problem 1a: 6/7
The representation invariant for `IntQueue2` ought to be:
```
entries != null && 0 <= front < entries.length && 0 <= size <= entries.length
```

- Problem 1b: 1/1
- Problem 1c: 5/6
For #3, note that the method is private, so it cannot directly cause
representation exposure.

- Problem 2: 3/3
- Problem 3: 2/3
This is not clear-box testing, because you haven't implemented anything yet!

### Design: 2/3

### Documentation & Specification (including JavaDoc): 3/3

### Testing (test suite quality & implementation): 3/3

### Code quality (code stubs/skeletons only, nothing else): 3/3

### Mechanics: 3/3

#### Overall Feedback

Seems like you're missing an operation or two - you have a method `childrenOf`
for obtaining the edge labels of outedges on a node, but how do you get the node
labels of the children?

You're on the right track with documentation.

Your test suite is a little basic but okay for now.

#### More Details

The JUnit assertions testing for exceptions won't work as far as I know.  There
is specific syntax we introduced for doing this - see section slides.
