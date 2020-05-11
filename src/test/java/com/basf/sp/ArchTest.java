package com.basf.sp;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.basf.sp");

        noClasses()
            .that()
            .resideInAnyPackage("com.basf.sp.service..")
            .or()
            .resideInAnyPackage("com.basf.sp.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.basf.sp.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
