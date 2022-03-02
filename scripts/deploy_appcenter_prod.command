
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )" 

cd "$DIR"
cd ".."

read -p "Skip building and archiving app(y/n)? " SKIP_ARCHIVE_STRING
SKIP_ARCHIVE=false
if [ "$SKIP_ARCHIVE_STRING" == "y" ] || [ "$SKIP_ARCHIVE_STRING" == "yes" ]; then SKIP_ARCHIVE=true; fi

bundle exec fastlane deploy_appcenter_prod skip_archive:"$SKIP_ARCHIVE"