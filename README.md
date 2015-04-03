# compare-sheets

## What is this?
Dear README, I never thought this would happen to me, but I got my self in a situation where I had to compare two excel sheets and give a report. And because I am lazy in respect that I don't want to compare these sheets manually, I wrote myself a small tool that does this work for me.

This simple Java application basically loads 2 excel sheets of different structures containing names and vacation entries. **Nobody but me will probably need this**, but I though I'll share it anyway.

For example **sheet A** contains names and worked hours/per-day for a single month as follows:<br>
<table class="tg">
  <tr>
    <th class="tg-031e">...</th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">Name</td>
    <td class="tg-031e">Surname</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">1</td>
    <td class="tg-031e">2</td>
    <td class="tg-031e">3</td>
    <td class="tg-031e">4</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">31</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">Joe</td>
    <td class="tg-031e">Doe</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">0</td>
    <td class="tg-031e">0</td>
    <td class="tg-031e">4</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">0</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">Joe</td>
    <td class="tg-031e">Doe</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">4</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">Foo</td>
    <td class="tg-031e">Bar</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">0</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">Moe</td>
    <td class="tg-031e">Goo</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">0</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">8</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
</table>

And **sheet B** contains names and vacation-flags for each day of the year as follows:<br>
<table class="tg">
  <tr>
    <th class="tg-031e">...</th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
    <th class="tg-031e"></th>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">Name, Surname</td>
    <td class="tg-031e"></td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">1</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">31</td>
    <td class="tg-031e">1</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">28</td>
    <td class="tg-031e">1</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">31</td>
    <td class="tg-031e">...</td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">Joe, Doe</td>
    <td class="tg-031e"></td>
    <td class="tg-031e">...</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">John, Appleseed</td>
    <td class="tg-031e"></td>
    <td class="tg-031e">...</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e">V</td>
    <td class="tg-031e">V</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">Foo, Bar</td>
    <td class="tg-031e"></td>
    <td class="tg-031e">...</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">Moe, Goo</td>
    <td class="tg-031e"></td>
    <td class="tg-031e">...</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e">V</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e">...</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
</table>

If **sheet A** contains a total of **0** working hours for some day for a given person **X**, that person must have a **V** (vacation) entry in **sheet B** for that day of the year. See **Moe Goo** above.

If that is not the case for a person, we have an inconsistency between the two sheets. 

This Java application finds and prints out the persons that are common in both sheets and have such inconsistencies. The application excludes weekends and holidays (load into the application as .xml) in its consistency check.

###How to Import into Eclipse
* **File** -> **Import...** -> **Existing Maven Projects**
* Click **Next**
* Click **Browse...** for the **Root Directory**
* Select and open **app**
* Click **Finish**
* Do a mvn update on **app**

