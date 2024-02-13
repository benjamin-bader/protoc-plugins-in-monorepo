package com.example

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec

fun main() {
    val req = CodeGeneratorRequest.parseFrom(System.`in`)
    val resp = generate(req)
    resp.writeTo(System.out)
}

fun generate(req: CodeGeneratorRequest): CodeGeneratorResponse {
    val resp = CodeGeneratorResponse.newBuilder()

    val greetBuilder = FunSpec.builder("greet")

    for (file in req.protoFileList) {
        greetBuilder.addStatement("println(%S)", "Hello, ${file.name}!")
    }

    val typeSpec = TypeSpec.classBuilder("HelloWorld")
        .addFunction(greetBuilder.build())
        .build()

    val fileSpec = FileSpec.builder("com.example.gen", "HelloWorld.kt")
    fileSpec.addType(typeSpec)

    resp.addFileBuilder()
        .setName("HelloWorld.kt")
        .setContent(fileSpec.build().toString())

    return resp.build()
}
