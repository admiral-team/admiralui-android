DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

cd "$DIR"
cd ".."

read -p "Enter external version: " EXTERNAL_VERSION
read -p "Enter internal version: " INTERNAL_VERSION
bundle exec fastlane set_version external_version:"$EXTERNAL_VERSION" internal_version:"$INTERNAL_VERSION"