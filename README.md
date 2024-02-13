This project shows how, in a multimodule Gradle project, one can define a protoc plugin in one module, and consume it as part of another module's build.

It's convoluted.  I wish it were easier.

To run:
`./gradlew :plugin-consumer:run`

If everything's working, you can expect to see, amidst the Gradle logspam, the line "Hello, example.proto!".  This would come from a source file that's generated by our example protoc plugin.

## How does this work?

In short, we took the principles outlined in https://docs.gradle.org/current/userguide/cross_project_publications.html#sec:simple-sharing-artifacts-between-projects, and Made It Work with the protobuf gradle plugin.  It isn't pretty, and it leaves a bit to be desired in the maintainability department, but this is the best I know how to do it.

## When would you want this?

You want this if you:
- have a monorepo
- use protobuf, and in particular Google's protobuf-gradle-plugin
- want to hook in to protobuf code-generation

If one of those isn't true, you don't need this.  In a multi-repo situation, the obvious and easiest choice is to just publish a standalone artifact to a Maven repository.  If you don't want to generate custom code based on your protos, you don't need any plugins at all.  If you aren't using protobuf, well.  That's obvious, hopefully.

## Why is this a repo?

Because it took me hella time to figure out how to make this work at all.  Maybe it's helpful.  Maybe someone out there has a better approach.

## But really, how do you use this?

Follow the pattern of ':protoc-plugin' to author a protoc plugin in the form of a shadow jar application - that is, a "fat" jar containing all of your dependencies - and "publish" it in an outgoing configuration.

To consume it, do the convoluted stuff in ':plugin-consumer'.

If you're going to do this more than once, it's fairly straightforward to encode this in a Gradle plugin, in an included build.