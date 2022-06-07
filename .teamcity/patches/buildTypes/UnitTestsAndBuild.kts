package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.failureConditions.BuildFailureOnText
import jetbrains.buildServer.configs.kotlin.v2018_2.failureConditions.failOnText
import jetbrains.buildServer.configs.kotlin.v2018_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'UnitTestsAndBuild'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("UnitTestsAndBuild")) {
    check(artifactRules == """
        storybook-dist => storybook-dist.zip
        %teamcity.build.workingDir%/npmlogs/*.log=>npmlogsssssssssssssssssssss
        coverage => coverage.zip
        npm-ls.log
        lerna-debug.log
    """.trimIndent()) {
        "Unexpected option value: artifactRules = $artifactRules"
    }
    artifactRules = """
        storybook-dist => storybook-dist.zip
        dist => dist.zip
        %teamcity.build.workingDir%/npmlogs/*.log=>npmlogsssssssssssssssssssss
        coverage => coverage.zip
        npm-ls.log
        lerna-debug.log
    """.trimIndent()

    expectSteps {
        script {
            name = "Test And Build"
            scriptContent = """
                #!/bin/bash
                set -e -x

                node -v
                npm -v

                apt update
                apt install g++ gcc make python -y
                mkdir node_modules && mkdir -p node_modules/.cache && chmod -R 777 node_modules/.cache
                npm install

                npm run typecheck-ci
                npm run test-ci
                npm run build
                npm run build-examples
            """.trimIndent()
            dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
            dockerImage = "satantime/puppeteer-node:14.18"
        }
    }
    steps {
        update<ScriptBuildStep>(0) {
            clearConditions()
            scriptContent = """
                #!/bin/bash
                set -e -x

                node -v
                npm -v

                # Debugging
                ls
                git status

                apt update
                apt install g++ gcc make python -y
                npm install
                npm run bootstrap

                # Debugging
                npm ls > npm-ls.log || true

                npm run typecheck-ci
                npm run test-ci
                npm run build
                npm run build-examples
            """.trimIndent()
        }
    }

    failureConditions {
        val feature1 = find<BuildFailureOnText> {
            failOnText {
                conditionType = BuildFailureOnText.ConditionType.CONTAINS
                pattern = "ERROR:"
                failureMessage = "console.error appeared in log"
                reverse = false
            }
        }
        feature1.apply {
            enabled = false
        }
    }
}
