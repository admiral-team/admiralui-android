DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

cd "$DIR"
cd ".."

BRANCH=$(git rev-parse --abbrev-ref HEAD)

bundle exec fastlane upload_nexus_lib branch:"$BRANCH"