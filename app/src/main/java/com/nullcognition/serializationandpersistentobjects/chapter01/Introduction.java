package com.nullcognition.serializationandpersistentobjects.chapter01;

/**
 * Created by ersin on 19/04/15 at 7:58 PM
 */

// initial problem is to structure data

public class Introduction {

   // presistent object - at runtime will work as a normal object, between runs will be stored on disk or virtual memory,
   // movement between the tow are transparent, not involving a database, and is automatic

   // serialization - an implementation that is persistant but not automatic

   // Persistence > Data structures > UML
   // |-> Allocation > Data structures

   // aim - use persistent objects instead of a database, remove pointers/references from client code, manage data structures like databases,
   // incrlude structural patterns in data strucutre libraries, understand UML of such designs

/*
   HashMap<Library, Book>              books;
   HashMap<Library, Author>            authors;
   LinkedList<Library, Publisher>      publishers;
   ManyToMany<Book, WrittenBy, Author> writtenBy;
   LinkedList<Book, Publisher>         publisher;
   String<Book>                        title;
   String<Author>                      authorName;
   String<Publisher>                   publisherName;
*/
// then connect the pieces via new book, add book to library, new writteby, .add book and author, remove books publisher link, save the lib
}

class WrittenBy {}

class Library {

   int bookCount;
}

class Author {

}

class Book {

   int location;
   int year;
}

class Publisher {

   int bookCount;
}

// 1.3 Comparing pointers and references -------------------------
// Java then C++
class A {

   A next;

}

class APlus {

   A ptrNext; // should be A *next;
}

class Main {

   {
      // java
	  A ap, as;
	  ap = new A();
	  as = new A();
	  ap.next = as;
   }

   {
	  // c++
	  A ap, a = null; // should be A *ap, a;
	  ap = new A();
	  a.next = ap;
	  // ap -> next = &a;
   }

   // null ending chains
   {
	  A root = new A();
	  root.next = new A();
	  root.next.next = new A();
	  root.next.next.next = null;

	  // moving roots next to a new chain will cause the next.next and next.next.next to be garbage collected
	  root.next = (new A()).next = (new A()).next = (new A()).next = null;

	  // moving element without disconnecting will casue infinite traversal loops
	  root.next = root.next.next.next;

	  // using ring instead of null ending chains
	  root.next = (new A()).next = root;

	  // via public void append (A toTail){
      A toTail = new A();

	  A tail = root;
      while(! tail.next.equals(root)){
         tail = tail.next; // will traverse till the tail is reached
      }
      A tempRoot = tail.next;
	  tail.next = toTail;
      toTail.next = tempRoot;
   }
}

// using collections as first class entities support only uni-directional collections, vectors, trees, lists, but still reveals pointers
// and cannot support data structures such as Aggregate, ManyToMany, Graph, FiniteStateMachine, which require adding members (usually pointers)
// to more than one class

// javas persistance is easy because the compiler knows everything about the classes, objects, embedded references(pointers)
// When you invoke serialization on an object, it saves not only the object itself but, recursively, also all the objects it
// can reach via references.

// java serialization handles loops, but may stackoverflow if dealing with long chain of references, two classes have containel holding objects
// of the other class,

// (1) Implementation which causes a stack overflow
/*
class Book implements Serializable {
   NotJavaContainer<Author> authors;
}
class Author implements Serializable {
   NotJavaContainer<Book> books;
}
// (2) Java container which causes a stack overflow //
class Book implements Serializable {

   ArrayList<Author> authors;
}

class Author implements Serializable {

   ArrayList<Book> books;
}
// (3) Desperate man’s ManyToMany does not crash,
//
but is
neither clean
nor efficient

class Book implements Serializable {}

class Author implements Serializable {}

class Library implements Serializable {

   HashMap<Book, Container<Author>> booksToAuthors;
   HashMap<Author, Container<Book>> authorsToBooks;
}
*/

// Useful Trick No.1. When replacing references by hash tables, we can build bi-directional associations without inserting references
// into the participating objects. That can be useful in some situations, but it obscures the true purpose of what we are building,
// and requires more memory space and time to traverse.
