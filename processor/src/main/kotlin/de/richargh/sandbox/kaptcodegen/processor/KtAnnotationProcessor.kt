package de.richargh.sandbox.kaptcodegen.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asTypeName
import de.richargh.sandbox.kaptcodegen.annnotations.Interesting
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
class KtAnnotationProcessor: AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Interesting::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(Interesting::class.java).forEach {
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "${it.simpleName} is interesting.")
            val pack = processingEnv.elementUtils.getPackageOf(it).toString()
            generateClass(it, pack)
        }
        return true
    }

    private fun generateClass(klass: Element, pack: String) {
        val fileName = "${klass.simpleName}KsonUtil"
        val string = StringBuilder("return \"{ ")
        klass.enclosedElements.forEach {
            println("$it ${it.kind}")
            when (it.kind) {
                ElementKind.FIELD -> {
                    string.append("\\\"")
                    string.append(it)
                    string.append("\\\": ")
                    string.append("\\\"")
                    string.append("\$")
                    string.append(it)
                    string.append("\\\"")
                    string.append(' ')
                }
                else              -> {
                }
            }
        }
        string.append("}\"")
        val file = FileSpec.builder(pack, fileName)
                .addFunction(FunSpec.builder("toJson")
                                     .returns(String::class)
                                     .receiver(klass.asType().asTypeName())
                                     .addStatement(string.toString())
                                     .build())
                .build()

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir, "$fileName.kt"))
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}