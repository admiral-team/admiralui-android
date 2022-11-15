package main

import (
	"fmt"
	"log"
	"os"

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
	case "createRelease":
		githubToken := goDotEnvVariable("GITHUB_TOKEN")
		tgToken := goDotEnvVariable("TELEGRAM_API_TOKEN")
		client.ReleaseAndroid(githubToken, tgToken)
	default:
		fmt.Println("Unknown command")
	}
}

func goDotEnvVariable(key string) string {
	err := godotenv.Load(".env.secret")

	if err != nil {
		log.Fatalf("Error loading .env file: " + err.Error())
	}

	return os.Getenv(key)
}
