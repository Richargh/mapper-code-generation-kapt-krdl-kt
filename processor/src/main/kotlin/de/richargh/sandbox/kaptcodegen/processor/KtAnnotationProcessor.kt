package de.richargh.sandbox.kaptcodegen.processor

import com.google.auto.service.AutoService
import de.richargh.sandbox.kaptcodegen.annnotations.Interesting
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
class KtAnnotationProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Interesting::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(Interesting::class.java).forEach {
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "${it.simpleName} is interesting.")
        }
        return true
    }
}