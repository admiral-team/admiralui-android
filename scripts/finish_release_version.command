DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

cd "$DIR"
cd ".."

LAST_VERSION_NUMBER=$(git describe --tags `git rev-list --tags --max-count=1`)
read -p "Enter external version: " EXTERNAL_VERSION

bundle exec fastlane finish_release_version external_version:"$EXTERNAL_VERSION"

read -p "Commit changes(y/n) " COMMIT_CHANGES

if [ "$COMMIT_CHANGES" == "y" ] || [ "$COMMIT_CHANGES" == "yes" ]
then
    git commit -a -m "Release version $EXTERNAL_VERSION"
    git push
fi