### Answers Score: 5/10
- Problem 1: 4/7

Missing disadvantages for graph representations.

- Problem 4: 1/3

While you may or may not be correct, we ask that you phrase your answers more
concisely.

### Design: 2/3

### Documentation & Specification (including JavaDoc): 2/3

### Code quality (code and internal comments including RI/AF): 2/3

### Testing (test suite quality & implementation): 3/3

### Mechanics: 3/3

#### Overall Feedback

Some design and implementation choices need changes - tests are overall good.

#### More Details

Class names should be Capitalized.

Constants like your debug flag should be in ALL_CAPS.

Specifying both `@spec.requires !A` and `@throws SampleException when A` is
contradictory, since, on the one hand, you are declaring the behavior of the
method under the condition `A` to be undefined, and, on the other, you are
declaring it to be defined (to throw an exception) under the condition `A`.

Your debug flag is disabling assertions that cost essentially nothing.  Make
sure to leave the one-liner assertions outside the `if` statement of the debug
flag - they should always be enabled.

Your checkRep does not guarantee that edges are not null - which could be a 
problem with rep exposure.

Code is hard to read - needs more whitespace.

name, getName etc are unnecessary for your graph. (e.g. does the HashMap ADT
have a field for name?)

Edge and Graph implementation tests should be separate since they are now 
separate ADTs.

