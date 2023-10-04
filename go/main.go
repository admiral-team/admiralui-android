package main

import (
	"fmt"
	"log"
	"os"
	"os/exec"


	"github.com/admiral-team/admiral-tools/client"
	"github.com/joho/godotenv"
)

func main() {
	figmaToken := goDotEnvVariable("FIGMA_ACCESS_TOKEN")

	switch os.Args[1] {
	case "loadDocumentation":
		documentationFile := goDotEnvVariable("FIGMA_DOCUMENTATION_FILE_KEY")
		client.LoadDocumentation(figmaToken, documentationFile, "../demo/src/main/assets")
	case "loadImages":
		imagesFile := goDotEnvVariable("FIGMA_IMAGES_FILE_KEY")
		client.LoadImagesAndroid(figmaToken, imagesFile)
	case "uploadNexus":
        os.Chdir("../")
	    cmd := exec.Command("./gradlew", "assemble")
        output, err := cmd.Output()
        if err != nil {
        	fmt.Println(err)
        	return
        }

        os.Chdir("go/")
        fmt.Println(string(output))

		loadFilesToNexus()
	default:
		fmt.Println("Unknown command")
	}
}

func loadFilesToNexus() {
	loadConcreteFileToNexus("admiral-resources", "admiral-resources.aar")
	loadConcreteFileToNexus("admiral-themes", "admiral-themes.aar")
	loadConcreteFileToNexus("admiral-uikit", "admiral-uikit.aar")
	loadConcreteFileToNexus("admiral-uikit-common", "admiral-uikit-common.aar")
}

func loadConcreteFileToNexus(artifactId string, fileName string) {
	version := os.Args[2]
	groupId := "admiralui-android"
	extention := "aar"
	nexusRepository := goDotEnvVariable("NEXUS_URL")
	nexusUsername := goDotEnvVariable("NEXUS_USERNAME")
	nexusPassword := goDotEnvVariable("NEXUS_PASSWORD")
	client.UploadNexus(groupId, artifactId, version, "../libs/"+fileName, extention, nexusRepository, nexusUsername, nexusPassword)
}

func goDotEnvVariable(key string) string {
	err := godotenv.Load(".env.secret")

	if err != nil {
		log.Fatalf("Error loading .env file: " + err.Error())
	}

	return os.Getenv(key)
}
