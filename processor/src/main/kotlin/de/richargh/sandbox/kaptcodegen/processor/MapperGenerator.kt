package de.richargh.sandbox.kaptcodegen.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import de.richargh.sandbox.kaptcodegen.annnotations.Mapper
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
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
            val pack = element.packageOf().toString()
            generateClass(className, pack)

            element.constructors { constructor ->
                println("$constructor ${constructor.kind} in ${constructor.enclosedElements}")

                constructor.parameterElements().forEach { parameter ->
                    println("Constructor argument: ${parameter.simpleName} : ${parameter.asType()}")
                    parameter.constructors {
                        println("-- ${it.parameters}")
                    }
                }
            }
        }
        return true
    }

    private fun ExecutableElement.parameterElements(): List<TypeElement> = parameters
            .map { variableElement -> processingEnv.typeUtils.asElement(variableElement.asType()) }
            .filterIsInstance<TypeElement>()

    private fun Element.packageOf() = processingEnv.elementUtils.getPackageOf(this)

    /**
     * Finds all constructors in an element, if there are any.
     *
     * Alternative necessary because the javax util variant fails with an java.lang.reflect.InvocationTargetException
     * I believe that is because some elements in Kotlin throw an exception when asking for their kind,
     * so you need to check their type first.
     *
     * @see javax.lang.model.util.ElementFilter.constructorsIn
     */
    private fun Element.constructors(handler: (ExecutableElement) -> Unit) {
        enclosedElements.forEach { enclosed ->
            if (enclosed is ExecutableElement && enclosed.kind == ElementKind.CONSTRUCTOR) {
                handler(enclosed)
            }
        }
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