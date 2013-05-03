= JBoss Tools Asciidoctor experiment

Experiment to check if can load and render asciidoc docs in Eclipse.

To import into eclipse we need to tell maven to go get the mvn dependencies. 

That is done the fastest by running:

   $ mvn -Dtycho.mode=maven clean dependency:copy-dependencies

And then `File > Import > Existing project` from Eclipse and the magic is in AsciidoctorView.

To test it, run this plugin from 'Eclipse with Run As... > Eclipse Application'

Open the Asciidoctor View.

Select something in a project (anything, that will trigger a selection changed event) - when creating asciidoctor it currently fails to load the asciidoctor resources.

Fix it at https://github.com/maxandersen/jbosstools-asciidoctor/blob/master/plugins/org.jboss.tools.asciidoctor.ui/src/org/jboss/tools/asciidoctor/ui/AsciidoctorView.java#L45




 