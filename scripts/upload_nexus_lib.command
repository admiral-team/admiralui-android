DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

cd "$DIR"
cd ".."

./gradlew publish -PrepositoryType="Nexus"