package de.richargh.sandbox.kaptcodegen.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import de.richargh.sandbox.kaptcodegen.annnotations.Mapper
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class MapperGenerator: AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Mapper::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }

    override fun process(set: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(Mapper::class.java).forEach { element ->
            val className = element.simpleName.toString()
            println("Processing: $className")
            val pack = processingEnv.elementUtils.getPackageOf(element).toString()
            generateClass(className, pack)

            element.enclosedElements.forEach { enclosed ->
                when(enclosed.kind) {
                    ElementKind.CONSTRUCTOR -> {
                        val constructor = enclosed as ExecutableElement
                        println("$enclosed ${enclosed.kind} in ${enclosed.enclosedElements}")

                        constructor.parameters.forEach { parameter ->
                            val type = parameter.asType()

                            val parameterElement = processingEnv.typeUtils.asElement(type)
                            when(parameterElement){
                                is TypeElement -> {
                                    println("Constructor argument: ${parameter.simpleName} : $type")
                                    parameterElement.enclosedElements.forEach {
                                        if(it is ExecutableElement && it.kind == ElementKind.CONSTRUCTOR){
                                            println("-- ${it.parameters}")
                                            // processingEnv.typeUtils.getPrimitiveType()
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            }
        }
        return true
    }


    private fun generateClass(className: String, pack: String) {
        val fileName = "${className}Mapper"
        val file = FileSpec.builder(pack, fileName)
                .addType(TypeSpec.classBuilder(fileName)
                                 .addFunction(FunSpec.builder("map")
                                                      .addParameter(className, ClassName(pack, className))
                                                      .addStatement("""return $className("bar", 21, Person("Me"))""")
                                                      .build())
                                 .build())
                .build()

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir, "$fileName.kt"))
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}