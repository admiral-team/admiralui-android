package main

import (
	"fmt"
	"log"
	"os"
	"strconv"

	"github.com/admiral-team/admiral-tools/client"
	"github.com/joho/godotenv"
)

func main() {
	token := goDotEnvVariable("FIGMA_ACCESS_TOKEN")
	imagesFile := goDotEnvVariable("FIGMA_IMAGES_FILE_KEY")
	documentationFile := goDotEnvVariable("FIGMA_DOCUMENTATION_FILE_KEY")

	switch os.Args[1] {
	case "loadDocumentation":
		client.LoadDocumentation(token, documentationFile, "../demo/src/main/assets")
	case "loadImages":
		client.LoadImagesAndroid(token, imagesFile)
	case "reportTelegram":
		telegramChatId, _ := strconv.Atoi(goDotEnvVariable("TELEGRAM_PROD_CHAT_ID"))
		buildInfo := configureBuildInfo(os.Args[2])
		formatedBuildInfoTelegram := buildInfo.formatted_build_info_telegram()
		client.SendTextToTelegramChat(telegramChatId, formatedBuildInfoTelegram, goDotEnvVariable("TELEGRAM_API_TOKEN"))
	case "createRelease":
		buildInfo := configureBuildInfo(os.Args[2])
		releaseBody := buildInfo.telegram_release_message()
		telegramChatId, _ := strconv.Atoi(goDotEnvVariable("TELEGRAM_PROD_CHAT_ID"))
		client.CreateRelease(os.Getenv("REPO"), buildInfo.External_version, os.Args[3])
		client.SendTextToTelegramChat(telegramChatId, releaseBody, os.Args[5])
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
