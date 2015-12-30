/*
 * Copyright (C) 2015 Hannes Dorfmann
 * Copyright (C) 2015 Tickaroo, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tickaroo.tikxml.processor.scanning

import com.google.common.truth.Truth
import com.google.testing.compile.JavaFileObjects
import com.google.testing.compile.JavaSourceSubjectFactory
import com.google.testing.compile.JavaSourcesSubject
import com.tickaroo.tikxml.annotation.*
import com.tickaroo.tikxml.processor.XmlProcessor
import org.junit.Test
import javax.tools.JavaFileObject

/**
 *
 * @author Hannes Dorfmann
 */
class AnnotationOnlyScanStrategyTest {


    @Test
    fun multipleAnnotationOnField1() {
        val componentFile = JavaFileObjects.forSourceLines("test.MultipleAnnotations1",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${Attribute::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "import ${PropertyElement::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class MultipleAnnotations1 {",
                "   @${Attribute::class.java.simpleName}",
                "   @${Element::class.java.simpleName}",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Fields can ONLY be annotated with one of the following")
    }

    @Test
    fun multipleAnnotationOnField2() {
        val componentFile = JavaFileObjects.forSourceLines("test.MultipleAnnotations2",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${Attribute::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "import ${PropertyElement::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class MultipleAnnotations2 {",
                "   @${Element::class.java.simpleName}",
                "   @${PropertyElement::class.java.simpleName}",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Fields can ONLY be annotated with one of the following")
    }

    @Test
    fun multipleAnnotationOnField3() {
        val componentFile = JavaFileObjects.forSourceLines("test.MultipleAnnotations3",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${Attribute::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "import ${PropertyElement::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class MultipleAnnotations3 {",
                "   @${PropertyElement::class.java.simpleName}",
                "   @${Attribute::class.java.simpleName}",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Fields can ONLY be annotated with one of the following")
    }

    @Test
    fun multipleAnnotationOnField4() {
        val componentFile = JavaFileObjects.forSourceLines("test.MultipleAnnotations4",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${Attribute::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "import ${PropertyElement::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class MultipleAnnotations4 {",
                "   @${Attribute::class.java.simpleName}",
                "   @${PropertyElement::class.java.simpleName}",
                "   @${Element::class.java.simpleName}",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Fields can ONLY be annotated with one of the following")
    }

    @Test
    fun inlineListOnNotListType() {
        val componentFile = JavaFileObjects.forSourceLines("test.InlineListOnNotListType",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${InlineList::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class InlineListOnNotListType {",
                "   @${InlineList::class.java.simpleName}",
                "   @${Element::class.java.simpleName}",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The annotation @InlineList is only allowed on java.util.List types, but the field 'aField' in class test.InlineListOnNotListType is of type java.lang.String")
    }

    @Test
    fun inlineListOnNotListTypeWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.InlineListOnNotListTypeWithPolymorphism",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${InlineList::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class InlineListOnNotListTypeWithPolymorphism {",
                "   @${InlineList::class.java.simpleName}",
                "   @${Element::class.java.simpleName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=java.lang.Object)",
                "    )",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The annotation @InlineList is only allowed on java.util.List types, but the field 'aField' in class test.InlineListOnNotListTypeWithPolymorphism is of type java.lang.String")
    }

    @Test
    fun inlineListOnListType() {
        val componentFile = JavaFileObjects.forSourceLines("test.InlineListOnListType",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${InlineList::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class InlineListOnListType {",
                "   @${InlineList::class.java.simpleName}",
                "   @${Element::class.java.simpleName}",
                "   java.util.List<String> aList;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()
    }

    @Test
    fun inlineListOnArrayListType() {
        val componentFile = JavaFileObjects.forSourceLines("test.InlineListOnArrayListType",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class InlineListOnArrayListType {",
                "   @${InlineList::class.java.canonicalName}",
                "   @${Element::class.java.canonicalName}",
                "   java.util.ArrayList<String> aList;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()
    }

    @Test
    fun inlineListOnLinkedListType() {
        val componentFile = JavaFileObjects.forSourceLines("test.InlineListOnLinkedListType",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class InlineListOnLinkedListType {",
                "   @${InlineList::class.java.canonicalName}",
                "   @${Element::class.java.canonicalName}",
                "   java.util.LinkedList<String> aList;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()
    }

    @Test
    fun polymorphicTypeIsPrivateClass() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicClassIsPrivate",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicClassIsPrivate {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerPrivateClass.class)",
                "    )",
                "   Object aField;",
                "",
                "private class InnerPrivateClass {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("@${ElementNameMatcher::class.simpleName} does not allow private classes. test.PolymorphicClassIsPrivate.InnerPrivateClass is a private class!")
    }

    @Test
    fun polymorphicTypeIsProtectedClass() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicClassIsProtected",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicClassIsProtected {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerProtectedClass.class)",
                "    )",
                "   Object aField;",
                "",
                "protected class InnerProtectedClass {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("@${ElementNameMatcher::class.simpleName} does not allow protected classes. test.PolymorphicClassIsProtected.InnerProtectedClass is a protected class!")
    }

    @Test
    fun polymorphicTypeHasNoPublicConstructor() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicClassHasNoPublicConstructor",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicClassHasNoPublicConstructor {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass.class)",
                "    )",
                "   Object aField;",
                "",
                " public class InnerClass {",
                "    private InnerClass() {}",
                " }",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Class test.PolymorphicClassHasNoPublicConstructor.InnerClass used in @${ElementNameMatcher::class.simpleName} must provide an public empty (parameter-less) constructor")
    }

    @Test
    fun polymorphicTypeHasNoEmptyConstructor() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicClassHasNoEmptyConstructor",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicClassHasNoEmptyConstructor {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass.class)",
                "    )",
                "   Object aField;",
                "",
                " public class InnerClass {",
                "    public InnerClass(int a) {}",
                " }",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Class test.PolymorphicClassHasNoEmptyConstructor.InnerClass used in @${ElementNameMatcher::class.simpleName} must provide an public empty (parameter-less) constructor")
    }

    @Test
    fun polymorphicTypeIsInterface() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicTypeIsInterface",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicTypeIsInterface {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass.class)",
                "    )",
                "   Object aField;",
                "",
                " public interface InnerClass {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("@${ElementNameMatcher::class.simpleName} only allows classes. test.PolymorphicTypeIsInterface.InnerClass is a not a class!")
    }

    @Test
    fun polymorphicTypeIsEnum() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicTypeIsEnum",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicTypeIsEnum {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass.class)",
                "    )",
                "   Object aField;",
                "",
                " public enum InnerClass {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("@${ElementNameMatcher::class.simpleName} only allows classes. test.PolymorphicTypeIsEnum.InnerClass is a not a class!")
    }

    @Test
    fun polymorphicTypeIsNotSubType() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicTypeIsNotSubType",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicTypeIsNotSubType {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass.class)",
                "    )",
                "   String aField;",
                "",
                " public class InnerClass {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The type test.PolymorphicTypeIsNotSubType.InnerClass must be a sub type of java.lang.String. Otherwise this type cannot be used in @${ElementNameMatcher::class.simpleName} to resolve polymorphis")
    }

    @Test
    fun polymorphicTypeIsSubType() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicTypeIsSubType",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicTypeIsSubType {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass.class)",
                "    )",
                "   Object aField;",
                "",
                " public class InnerClass {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()
    }

    @Test
    fun polymorphicEmptyXmlName() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicEmptyXmlName",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicEmptyXmlName {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"\" , type=InnerClass.class)",
                "    )",
                "   Object aField;",
                "",
                " public class InnerClass {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The xml element name in @${ElementNameMatcher::class.simpleName} cannot be empty")
    }

    @Test
    fun polymorphicBlankXmlName() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicBlankXmlName",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicBlankXmlName {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"    \" , type=InnerClass.class)",
                "    )",
                "   Object aField;",
                "",
                " public class InnerClass {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The xml element name in @${ElementNameMatcher::class.simpleName} cannot be empty")
    }

    @Test
    fun polymorphicElementNameInConflict() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicElementNameInConflict",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicElementNameInConflict {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass2.class),",
                "    })",
                "   Object aField;",
                "",
                " public class InnerClass1 {}",
                " public class InnerClass2 {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Conflict: A @${ElementNameMatcher::class.simpleName} with the name \"foo\" is already mapped to the type test.PolymorphicElementNameInConflict.InnerClass1 to resolve polymorphism. Hence it cannot be mapped to test.PolymorphicElementNameInConflict.InnerClass2 as well.")
    }

    @Test
    fun polymorphicElementNoNamingConflict() {
        val componentFile = JavaFileObjects.forSourceLines("test.PolymorphicElementNoNamingConflict",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class PolymorphicElementNoNamingConflict {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   Object aField;",
                "",
                " public class InnerClass1 {}",
                " public class InnerClass2 {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()
    }

    @Test
    fun elementDeclarationOnPrimitive() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementDeclarationOnPrimitive",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementDeclarationOnPrimitive {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   int aField;",
                "",
                " public class InnerClass1 {}",
                " public class InnerClass2 {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The type of field 'aField' in class test.ElementDeclarationOnPrimitive is not a class nor a interface. Only classes or interfaces can be annotated with @${Element::class.simpleName} annotation")
    }

    @Test
    fun elementOnInterfaceWithoutPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementOnInterfaceWithoutPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementOnInterfaceWithoutPolymorphism {",
                "   @${Element::class.java.canonicalName}",
                "   MyInterface aField;",
                "",
                " public interface MyInterface {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The type of field 'aField' in class test.ElementOnInterfaceWithoutPolymorphism is an interface. Since interfaces cannot be instantiated you have to specify which class should be instantiated (resolve polymorphism) manually by @${Element::class.simpleName}( typesByElement = ... )")
    }

    @Test
    fun elementOnInterfaceWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementOnInterfaceWithPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementOnInterfaceWithPolymorphism {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   MyInterface aField;",
                "",
                " public interface MyInterface {}",
                " public class InnerClass1 implements MyInterface{}",
                " public class InnerClass2 implements MyInterface{}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()

    }

    @Test
    fun elementOnInterfaceWithPolymorphismWrong() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementOnInterfaceWithoutPolymorphismWrong",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementOnInterfaceWithoutPolymorphismWrong {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   MyInterface aField;",
                "",
                " public interface MyInterface {}",
                " public class InnerClass1 implements MyInterface{}",
                " public class InnerClass2 {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The type test.ElementOnInterfaceWithoutPolymorphismWrong.InnerClass2 must be a sub type of test.ElementOnInterfaceWithoutPolymorphismWrong.MyInterface. Otherwise this type cannot be used in @${ElementNameMatcher::class.simpleName} to resolve polymorphism")

    }

    @Test
    fun elementOnAbstractClassWithoutPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementOnAbstractClassWithoutPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementOnAbstractClassWithoutPolymorphism {",
                "   @${Element::class.java.canonicalName}",
                "   MyClass aField;",
                "",
                " public abstract class MyClass {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The type of field 'aField' in class test.ElementOnAbstractClassWithoutPolymorphism is an abstract class. Since abstract classes cannot no be instantiated you have to specify which class should be instantiated (resolve polymorphism) manually by @${Element::class.simpleName}( typesByElement = ... )")
    }

    @Test
    fun elementOnAbstractClassWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementOnAbstractClassWithPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementOnInterfaceWithPolymorphism {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   MyClass aField;",
                "",
                " public abstract class MyClass {}",
                " public class InnerClass1 extends MyClass{}",
                " public class InnerClass2 extends MyClass{}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()

    }

    @Test
    fun elementOnAbstractWithPolymorphismWrong() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementOnAbstractClassWithPolymorphismWrong",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementOnAbstractClassWithPolymorphismWrong {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   MyClass aField;",
                "",
                " public abstract class MyClass {}",
                " public class InnerClass1 extends MyClass{}",
                " public class InnerClass2 {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The type test.ElementOnAbstractClassWithPolymorphismWrong.InnerClass2 must be a sub type of test.ElementOnAbstractClassWithPolymorphismWrong.MyClass. Otherwise this type cannot be used in @${ElementNameMatcher::class.simpleName} to resolve polymorphism")
    }

    @Test
    fun elementListAbstractClassWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementOnAbstractClassWithPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementOnInterfaceWithPolymorphism {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   java.util.List<MyClass> aField;",
                "",
                " public abstract class MyClass {}",
                " public class InnerClass1 extends MyClass{}",
                " public class InnerClass2 extends MyClass{}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()

    }

    @Test
    fun elementListInterfaceWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementOnAbstractClassWithPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementOnInterfaceWithPolymorphism {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   java.util.List<MyInterface> aField;",
                "",
                " public interface MyInterface {}",
                " public class InnerClass1 implements MyInterface{}",
                " public class InnerClass2 implements MyInterface{}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()

    }

    @Test
    fun elementListRawAbstractClassWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementListRawAbstractClassWithPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementListRawAbstractClassWithPolymorphism {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   java.util.List aField;",
                "",
                " public abstract class MyClass {}",
                " public class InnerClass1 extends MyClass{}",
                " public class InnerClass2 extends MyClass{}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()

    }

    @Test
    fun elementListRawInterfaceWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementListRawInterfaceWithPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementListRawInterfaceWithPolymorphism {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   java.util.List aField;",
                "",
                " public interface MyInterface {}",
                " public class InnerClass1 implements MyInterface{}",
                " public class InnerClass2 implements MyInterface{}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()

    }

    @Test
    fun elementListWildcardExtendsInterfaceWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementListWildcardInterfaceWithPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementListWildcardInterfaceWithPolymorphism {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   java.util.List<? extends MyInterface> aField;",
                "",
                " public interface MyInterface {}",
                " public class InnerClass1 implements MyInterface{}",
                " public class InnerClass2 implements MyInterface{}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()

    }

    @Test
    fun elementListWildcardExtendsInterfaceWithPolymorphismWrong() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementListWildcardExtendsInterfaceWithPolymorphismWrong",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementListWildcardExtendsInterfaceWithPolymorphismWrong {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   java.util.List<? extends MyInterface> aField;",
                "",
                " public interface MyInterface {}",
                " public class InnerClass1 implements MyInterface{}",
                " public class InnerClass2 {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The type test.ElementListWildcardExtendsInterfaceWithPolymorphismWrong.InnerClass2 must be a sub type of test.ElementListWildcardExtendsInterfaceWithPolymorphismWrong.MyInterface. Otherwise this type cannot be used in @${ElementNameMatcher::class.simpleName} to resolve polymorphism")

    }

    @Test
    fun elementListWildcardSuperInterfaceWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementListWildcardInterfaceWithPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementListWildcardInterfaceWithPolymorphism {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=GrandParent.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=Parent.class),",
                "    })",
                "   java.util.List<? super GrandParent> aField;",
                "",
                " public class GrandParent {}",
                " public class Parent extends GrandParent {}",
                " public class Child extends Parent {}",
                "}")


        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()

    }

    @Test
    fun elementListWildcardSuperInterfaceWithPolymorphismWrong() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementListWildcardSuperInterfaceWithPolymorphismWrong",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementListWildcardSuperInterfaceWithPolymorphismWrong {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=GrandParent.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=Parent.class),",
                "    })",
                "   java.util.List<? super GrandParent> aField;",
                "",
                " public class GrandParent {}",
                " public class Parent {}",
                " public class Child extends Parent {}",
                "}")


        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The type test.ElementListWildcardSuperInterfaceWithPolymorphismWrong.Parent must be a sub type of test.ElementListWildcardSuperInterfaceWithPolymorphismWrong.GrandParent. Otherwise this type cannot be used in @${ElementNameMatcher::class.simpleName} to resolve polymorphism")

    }

    @Test
    fun elementListWildcardInterfaceWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.ElementListWildcardInterfaceWithPolymorphism",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class ElementListWildcardInterfaceWithPolymorphism {",
                "   @${Element::class.java.canonicalName}(",
                "       typesByElement = {",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=InnerClass1.class),",
                "       @${ElementNameMatcher::class.qualifiedName}(elementName=\"bar\" , type=InnerClass2.class),",
                "    })",
                "   java.util.List<?> aField;",
                "",
                " public interface MyInterface {}",
                " public class InnerClass1 implements MyInterface{}",
                " public class InnerClass2 {}",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()

    }

}