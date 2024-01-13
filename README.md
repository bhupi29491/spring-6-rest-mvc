# Usage

**Steps to compare an online repository with local branch**

**Step 1: Add a Remote**

> If you haven't added the remote already, you can add it using the following command:
> git remote add myremote https://github.com/example/repo.git

**Step 2: Fetch Remote Branch Information**

> Fetch the latest information about branches from the remote repository:
> git fetch myremote

**Step 3: Checkout the Desired Branch**

> Now, you can create a new local branch and switch to it by using the following command:
> git checkout -b local_branch_name myremote/<branch-name-you-want-to-checkout>

