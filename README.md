= JBoss Tools Asciidoctor experiment

Experiment to check if can load and render asciidoc docs in Eclipse.

To import into eclipse we need to tell maven to go get the mvn dependencies. 

That is simplest done by running:

   $ mvn compile

And then `File > Import > Existing project` from Eclipse and the magic is in AsciidoctorView.

Currently the problem is that jruby does not seem to be able to load asciidoctor resources.

 