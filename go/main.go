package main

import (
	"fmt"
	"log"
	"os"

	"github.com/admiral-team/admiral-tools/figma"
	"github.com/joho/godotenv"
)

func main() {
	token := goDotEnvVariable("FIGMA_ACCESS_TOKEN")
	imagesFile := goDotEnvVariable("FIGMA_IMAGES_FILE_KEY")
	documentationFile := goDotEnvVariable("FIGMA_DOCUMENTATION_FILE_KEY")

	switch os.Args[1] {
	case "loadDocumentation":
		figma.LoadDocumentation(token, documentationFile, "../demo/src/main/assets")
	case "loadImages":
		figma.LoadImagesAndroid(token, imagesFile)
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
