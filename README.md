
OUTDATED: the development moved to Mylyn

The content of this repository is my personal one and not related to official JBoss Tools entry

# JBoss Tools Asciidoctor experiment

Experiment to check if can load and render asciidoc docs in Eclipse.

Demo: http://screencast.com/t/tkHe4nq7F

Contributions *very* welcome - this is just a proof-of-concept for now but
feel free to submit patches and/or fork it and make it something different :)

Open Eclipse bug is [Bug 418563 - support for Asciidoc ](https://bugs.eclipse.org/bugs/show_bug.cgi?id=418563)

Update site for 2014 build is <http://maxandersen.github.io/jbosstools-asciidoctor/>

For Ascii handling without plugin, see video [Basic eclipse asciidoc w/live reload](http://vimeo.com/66051125)

## How to build

To import into eclipse we need to tell maven to go get the mvn dependencies. 

That is done the fastest by running:

   $ mvn -Dtycho.mode=maven clean dependency:copy-dependencies

And then `File > Import > Existing project` from Eclipse and the magic is in AsciidoctorView.

To test it, run this plugin from 'Eclipse with Run As... > Eclipse Application' (you might need to set `-XX:MaxPermSize=256m` to avoid running out of permgen space)

Open the Asciidoctor View.

Open a file (any standard eclipse text based editor should work)

Start typing, any change triggers an update to the asciidoctor view.

## Outstanding issues

- The boot of asciidoctor is happening as a big hack - awaiting a solution over at https://github.com/asciidoctor/asciidoctor-java-integration/issues/22
- Renders the full document on every change (should have a "grace" period before updating)
- Loading asciidoctor on main event thread (should use a Future to avoid blocking UI)
- Handle images - awaiting https://github.com/asciidoctor/asciidoctor-java-integration/issues/25
- Render .adoc files/directories to html on save? (General idea: add asciidoc builder that you configure by adding directories as "asciidoc content" and the plugin will render these files/directories on "full build" and when files in here are opened treat them as asciidoc)





 
