Bioworld Android Test

Getting Set Up
1. Checkout our github test repo: https://github.com/gitbio/androidTest
2. WARNING: DO NOT WORK ON MASTER. We will delete everything and never look at your code.
3. Switch to the branch candidate/YOUR_NAME that one of us has set up for you in advance.
4. All of your code should be committed to this branch.  We're not just interested in your code, but also in how you write your commit messages and what you commit when. Please don't commit the entire codebase when you're done, keep your commits atomic.
5. When you've completed the test, create a pull request and fill in the appropriate fields.

Code Test
We'd like you to create an app working with an API (https://www.restcountries.eu) that lists countries in a RecyclerView, displays their flag and currency name in another Activity, and lets the user know if they've viewed a countries info already. You are allowed to use any third-party libraries you like and install them in your project however you'd like, but using maven is strongly preferred.

Requirements:
1. Retrieve all info from https://www.restcountries.eu
2. First view should be a RecyclerView (do not use a different type of adapter view) that displays a list of country names.
3. When the user selects a country from the first screen the app will navigate to an Activity that displays the country's flag and what type of currency they use.
4. When the user navigates back to the first screen there should be a checkmark to the left of the countries whose info they have viewed. These checkmarks should persist if the user force closes the app and restarts. Use official material design icons to display the 'viewed' indicator.
5. Persist all data using SharedPreferences or SQLite.
6. When you are done, submit a pull request for master explaining what has been done. Do not merge with master.
7. All code must be written according to the Google code guidlines for contributors, found here: https://source.android.com/source/code-style

Bonuses:
1. Looks pretty (proper spelling, indentation, code formatting, etc., and also adheres to material design guidelines for visual elements)
2. Scales properly on all phones and tablets
3. Uses the maven repository for all dependencies
