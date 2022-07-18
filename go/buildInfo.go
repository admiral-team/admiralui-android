package main

import (
	"encoding/json"
	"fmt"
)

type BuildInfo struct {
	Platform         string `json:"platform,omitempty"`
	Build_id         string `json:"BuildID,omitempty"`
	Version          string `json:"Version,omitempty"`
	Internal_version string `json:"InternalVersion,omitempty"`
	External_version string `json:"ExternalVersion,omitempty"`
	Short_version    string `json:"ShortVersion,omitempty"`
	Download_url     string `json:"download_url,omitempty"`
	Install_url      string `json:"InstallURL,omitempty"`
	Build_url        string `json:"build_url,omitempty"`
	Branch_name      string `json:"BranchName,omitempty"`
	Issue            int    `json:"Issue,string,omitempty"`
	TelegramChatId   string `json:"TelegramChatId,string,omitempty"`
	TelegramToken    string `json:"TelegramToken,string,omitempty"`
	ActionId         string `json:"ActionId,string,omitempty"`
	PullNumber       int    `json:"PullNumber,string,omitempty"`
}

func (buildInfo BuildInfo) formatted_build_info_git() string {
	var resultString string

	if buildInfo.Platform != "" {
		resultString += "Platform: " + buildInfo.Platform + "\n"
	}
	if buildInfo.Build_id != "" {
		resultString += "Build ID: " + buildInfo.Build_id + "\n"
	}
	if buildInfo.Version != "" {
		resultString += "Version: " + buildInfo.Version + "\n"
	}
	if buildInfo.Internal_version != "" {
		resultString += "Internal Version: " + buildInfo.Internal_version + "\n"
	}
	if buildInfo.Short_version != "" {
		resultString += "Short Version: " + buildInfo.Short_version + "\n"
	}
	if buildInfo.Branch_name != "" {
		resultString += "Branch Name: " + buildInfo.Branch_name + "\n"
	}
	if buildInfo.Build_url != "" {
		resultString += "Install URL: " + buildInfo.Build_url + "\n"
	}
	return resultString
}

func (buildInfo BuildInfo) build_failed_info(pullNumber string) string {
	var resultString string
	resultString += "🆘<strong>Build failed</strong>🆘" + "\n"
	if buildInfo.Branch_name != "" {
		resultString += "<strong>Branch name: </strong>" + buildInfo.Branch_name + "\n"
	}
	resultString += "<strong>Build Url: </strong>" + "https://github.com/admiral-team/admiralui-ios/pull/" + pullNumber + "/checks" + "\n"
	return resultString
}

func (buildInfo BuildInfo) telegram_release_message() string {
	var resultString string
	resultString += "Новый релиз! ✨" + "\n"
	resultString += "Platform: iOS" + "\n"
	resultString += "Link: https://github.com/admiral-team/admiralui-ios/releases/tag/" + buildInfo.External_version + "\n"
	return resultString
}

func (buildInfo BuildInfo) formatted_build_info_telegram() string {
	var resultString string

	if buildInfo.Platform != "" {
		resultString += "<strong>Platform: </strong>" + buildInfo.Platform + "\n"
	}
	if buildInfo.Build_id != "" {
		resultString += "<strong>Build ID: </strong>" + buildInfo.Build_id + "\n"
	}
	if buildInfo.Version != "" {
		resultString += "<strong>Version: </strong>" + buildInfo.Version + "\n"
	}
	if buildInfo.Internal_version != "" {
		resultString += "<strong>Internal Version: </strong>" + buildInfo.Internal_version + "\n"
	}
	if buildInfo.Short_version != "" {
		resultString += "<strong>Short Version: </strong>" + buildInfo.Short_version + "\n"
	}
	if buildInfo.Branch_name != "" {
		resultString += "<strong>Branch Name: </strong>" + buildInfo.Branch_name + "\n"
	}
	if buildInfo.Build_url != "" {
		resultString += "<strong>Install URL: </strong>" + buildInfo.Build_url + "\n"
	}
	return resultString
}

func configureBuildInfo(byString string) BuildInfo {
	buildInfo := BuildInfo{}
	err := json.Unmarshal([]byte(byString), &buildInfo)

	if err != nil {
		fmt.Println("Cannot Parse JSON From Command Line....", err)
	}
	return buildInfo
}
