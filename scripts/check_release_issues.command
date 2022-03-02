
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

cd "$DIR"
cd ".."

read -p "Enter status (If you press enter status will become "ВЫПОЛНЕНО"): " STATUS
read -p "Enter fix version number (If you press enter status will become current verion): " FIX_VERSION
bundle exec fastlane start_check_release_issues status:"$STATUS" fix_version:"$FIX_VERSION"