<!DOCTYPE html><!--  This site was created in Webflow. http://www.webflow.com  -->
<!--  Last Published: Sun Feb 21 2021 05:13:28 GMT+0000 (Coordinated Universal Time)  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html data-wf-page="5fcf5929bcd40b089b89362a" data-wf-site="5fcbddeaf750a0c0eb0dba3c">
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <head>
        <meta charset="utf-8">
        <title>Parkland Preschool, Early Childhood Education Info, Calgary</title>

        <meta name="description" content="About Us - Our preschool is located in the Parkland Community Center. We have been meeting preschooler’s needs in the Calgary SE location for over 17 years.">
        <meta content="School Information" property="og:title">
        <meta content="School Information" property="twitter:title">
        <meta content="width=100%, initial-scale=1" name="viewport">
        <meta content="Webflow" name="generator">
        <link rel="stylesheet" href="css/bootstrap/bootstrap.min.css" type="text/css">
        <link href="css/normalize.css" rel="stylesheet" type="text/css">
        <link href="css/webflow.css" rel="stylesheet" type="text/css">
        <link href="css/parkland-preschool-project.webflow.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js" type="text/javascript"></script>
        <script type="text/javascript">WebFont.load({google: {families: ["Merriweather:300,300italic,400,400italic,700,700italic,900,900italic", "Roboto:100,100italic,300,300italic,regular,italic,500,500italic,700,700italic,900,900italic", "Comic Neue:300,300italic,regular,italic,700,700italic"]}});</script>
        <!-- [if lt IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js" type="text/javascript"></script><![endif] -->
        <script type="text/javascript">!function (o, c) {
                var n = c.documentElement, t = " w-mod-";
                n.className += t + "js", ("ontouchstart"in o || o.DocumentTouch && c instanceof DocumentTouch) && (n.className += t + "touch")
            }(window, document);</script>
    </head>
    <body>

        <h1 class="heading-24">School Information</h1>
        <div class="w-container">
            
            <c:if test="${success != null && success}"><div class="alert alert-success">${successMssg}</div></c:if>
            <c:if test="${success != null && !success}"><div class="alert alert-danger">${successMssg}</div></c:if>
            
            <div data-duration-in="300" data-duration-out="100" class="w-tabs">
                <div class="tabs-menu-3 w-tab-menu">
                    <a data-w-tab="Tab 1" class="tab-link-tab-1-3 w-inline-block w-tab-link  <c:if test="${mode eq 'aboutus'}">w--current</c:if>">
                        <h3 class="heading-26">About Us</h3>
                    </a>
                    <a data-w-tab="Tab 2" class="tab-link-tab-2-3 w-inline-block w-tab-link">
                        <h3 class="heading-25">Readiness</h3>
                    </a>
                    <a data-w-tab="Tab 3" class="tab-link-tab-3-3 w-inline-block w-tab-link <c:if test="${mode eq 'contact'}">w--current</c:if>">
                        <h3 class="heading-27">Contact Information</h3>
                    </a>
                </div>
                <div class="w-tab-content">
                    <div data-w-tab="Tab 1" class="w-tab-pane">
                        <h3>About us</h3>
                        <img src="images/preschool-p-500.jpeg" alt="Preschool" style="margin: 20px;margin-left: 200px">
                        <p>Parkland Preschool is located in the Parkland Community Center and has been meeting preschooler’s needs for over 17 years (better: since 1990).  Our school has wonderfully bright, spacious and well equipped classrooms.  
                            Learning centers include rice and water tables, science, computer, dramatic play area, blocks, paint easels, climber, castle and much more.  We also offer a safe outdoor play area in the beautiful residential neighbourhood of Parkland.
                        </p>
                        <h3>Classes</h3>                     
                        <p>Parkland Preschool offers both a 3 year old and a 4 year old program.  Our 3 year old classes are on Tuesday and Thursday mornings from 9:00 am to 11:15 am.  
                            The 4 year old program runs on Monday, Wednesday and Friday morning from 9:00 am to 11:15 am or afternoons from 1:15 pm to 3:30 pm.
                        </p>
                        <div>
                            <a href="https://websites.godaddy.com/en-CA/editor/9fe34c1a-919f-43e9-8d5b-f241ecce42a2/349dff34-c7cb-4c99-8e1d-c75d4fcdefee/edit/0dfaadb6-3055-4fd7-9248-cd589e619c4d/mutator/contentCards/0/headline#Table_Time"><br></a>
                            <p>
                                <strong>Table Time: </strong>Each class begins with table time.  “Small motor” activities are set out on the tables so that the children can begin immediately upon arrival.  
                                These activities include puzzles, writing materials, pegboards, stacking and sorting games and small building pieces.  The children have a chance to work independently or together while they chat with their friends and teachers.
                            </p>
                            <a href="https://websites.godaddy.com/en-CA/editor/9fe34c1a-919f-43e9-8d5b-f241ecce42a2/349dff34-c7cb-4c99-8e1d-c75d4fcdefee/edit/0dfaadb6-3055-4fd7-9248-cd589e619c4d/mutator/contentCards/0/headline#Table_Time"><br></a>
                            <p>
                                <strong>Circle Time: </strong>During the group time, we discuss our theme and special events.  The “special helper” updates the daily calendar and the weather chart.  
                                We read a story that is usually theme related.  We also work on basic concepts such as colours, shapes numbers and letters.
                            </p>
                            <a href="https://websites.godaddy.com/en-CA/editor/9fe34c1a-919f-43e9-8d5b-f241ecce42a2/349dff34-c7cb-4c99-8e1d-c75d4fcdefee/edit/0dfaadb6-3055-4fd7-9248-cd589e619c4d/mutator/contentCards/0/headline#Table_Time"><br>‍</a>
                            <p>
                                <strong>Arts and Crafts: </strong>The children have the opportunity to work with a variety of different craft materials in an open-ended fashion.  
                                We focus on the process, not the end product, so what they make is not as important as the creative process itself.  
                                The children will experiment with paper and scissors, paint and glue, crayons and dozens of different kinds of craft materials.
                            </p>
                            <a href="https://websites.godaddy.com/en-CA/editor/9fe34c1a-919f-43e9-8d5b-f241ecce42a2/349dff34-c7cb-4c99-8e1d-c75d4fcdefee/edit/0dfaadb6-3055-4fd7-9248-cd589e619c4d/mutator/contentCards/0/headline#Table_Time"><br>‍</a>
                            <p>
                                <strong>Learning Centers: </strong>The children are welcome to move freely around the room to choose any of the games, equipment, or to play with their friends.  This period of socialization is a very important part of the program.  
                                The children learn to share cooperate and build friendships.  Our learning centers include a dramatic play center, reading area, listening and science center, painting easels, water table, building blocks, rice table, computer and rotating theme center.
                            </p>
                            <a href="https://websites.godaddy.com/en-CA/editor/9fe34c1a-919f-43e9-8d5b-f241ecce42a2/349dff34-c7cb-4c99-8e1d-c75d4fcdefee/edit/0dfaadb6-3055-4fd7-9248-cd589e619c4d/mutator/contentCards/0/headline#Table_Time"><br></a>
                            <p>
                                <strong>Snack: </strong>After tidying time, the children was their hands in preparation for a snack that the special helper has provided for the class.
                            </p>
                            <a href="https://websites.godaddy.com/en-CA/editor/9fe34c1a-919f-43e9-8d5b-f241ecce42a2/349dff34-c7cb-4c99-8e1d-c75d4fcdefee/edit/0dfaadb6-3055-4fd7-9248-cd589e619c4d/mutator/contentCards/0/headline#Table_Time"><br></a>
                            <p>
                                <strong>Gym/Games: </strong>Each day, the children have gym/game time.  They participate in group games and activities, both indoors and outdoors when possible, using a variety of equipment.
                            </p>
                            <a href="https://websites.godaddy.com/en-CA/editor/9fe34c1a-919f-43e9-8d5b-f241ecce42a2/349dff34-c7cb-4c99-8e1d-c75d4fcdefee/edit/0dfaadb6-3055-4fd7-9248-cd589e619c4d/mutator/contentCards/0/headline#Table_Time"><br></a>
                            <p>
                                <strong>Music: </strong>We wind down our day with music.  Over the course of the year, the children are exposed to many songs, games and musical activities with voice, picture books, instruments and recorded music. 
                            </p>
                        </div>
                    </div>  

                    <div data-w-tab="Tab 2" class="w-tab-pane w--tab-active">
                        <h3>Is your Child Ready for Preschool?</h3>
                        <img src="images/coloring.jpg" alt="Coloring" style="margin: 20px;" /><hr>
                        <h3>3 Year old Preschool Readiness Checklist</h3>
                        <div>
                            <h4>Emotional Development</h4>
                            <ul>
                                <li>Recognizes other people’s emotions</li>
                                <li>Takes turns and is able to share toys</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Attention & Independence</h4>
                            <ul>
                                <li>Listens to simple instructions</li>
                                <li>Sits still during story time</li>
                                <li>Can separate himself from you for a few hours</li>
                                <li>Enjoys doing things herself sometimes, such as getting dressed on her own</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Language, Art, and Math</h4>
                            <ul>
                                <li>Recognizes some shapes and colors</li>
                                <li>Recites the alphabet and recognize some letters</li>
                                <li>Expresses thoughts and needs verbally</li>
                                <li>Recites his full name</li>
                                <li>Counts to five</li>
                                <li>Draws with crayons or pencil</li>
                            </ul>
                        </div>
                        <hr>
                        <h3>4 Year old Preschool Readiness Checklist</h3>
                        <div>
                            <h4>Social skills</h4>
                            <ul>
                                <li>Initiates and maintains independent play (for example, plays alone in the sandbox, or role-plays independently)</li>
                                <li>Enjoys doing things on their own sometimes, such as reading, crafts or getting dressed</li>
                                <li>Can separate from you for several hours, such as an afternoon at a friend’s house or a sleepover at Grandma’s</li>
                                <li>Appears interested in going to a “big-kid” school, learning new things, and/or meeting new friends</li>
                                <li>Enjoys participating in group activities</li>
                                <li>Responds well to consistent routines, such as quiet time or naptime following lunch</li>
                                <li>Anticipates what comes next during the day (for example, knows that naptime follows lunch)</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Motor skills</h4>
                            <ul>
                                <li>Increases proficiency in gross motor skills, strength and balance, such as jumping in place, standing on one foot, running and kicking</li>
                                <li>Develops gross motor coordination, such as to navigate around obstacles
                                    Rides tricycles</li>
                                <li>Runs to kick a stationary ball</li>
                                <li>Improves hand-eye coordination when playing with building blocks and simple puzzles</li>
                                <li>Begins to improve pencil control by using fingers rather than the whole fist to grasp pencil and stylus</li>
                                <li>Begins to show left/right-handedness</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Reasoning & concept development</h4>
                            <ul>
                                <li>Matches like objects, mainly identical objects, or matches objects by shape and colors</li>
                                <li>Develops object permanence and understands that objects continue to exist even when out of sight</li>
                                <li>Shows interests in tinkering with objects by taking things apart and putting them back together</li>
                                <li>Explores with elements of nature, such as sand and water</li>
                                <li>Remembers short sequences of events of 2 to 3 steps</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Language skills</h4>
                            <ul>
                                <li>Uses language to communicate with others for a variety of purposes (for example, describing something, making requests, greeting someone, etc.)</li>
                                <li>Speaks clearly to be understood by others</li>
                                <li>Uses accepted language and communication styles (for example, using polite manners, using appropriate volume and tone)</li>
                                <li>Tells simple stories</li>
                                <li>Uses accepted nouns, verbs and adjectives in familiar contexts</li>
                                <li>Understands words for common categories (for example, toys, food, clothes)</li>
                                <li>Uses sentences with two phrases or concepts</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Reading</h4>
                            <ul>
                                <li>Holds a book properly and turns pages</li>
                                <li>Understands that words convey the message in a story</li>
                                <li>Recognizes the first letter of their own name</li>
                                <li>Knows some letter names</li>
                                <li>Knows the main characters in familiar stories</li>
                                <li>Enjoys reading books with others</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Writing</h4>
                            <ul>
                                <li>Holds a writing tool with a fist or finger grasp</li>
                                <li>Draws with a variety of tools (crayons, pens, pencils)</li>
                                <li>Scribble-writes in a linear fashion</li>
                                <li>Makes marks and refer to them as “my name”</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Math</h4>
                            <ul>
                                <li>Identifies some shapes such as circle, square and triangle</li>
                                <li>Understands and explores empty containers and full containers</li>
                                <li>Recognizes and matches small quantities to the number words 1, 2 and 3</li>
                                <li>Shows interest in numbers and recites some number words</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Science</h4>
                            <ul>
                                <li>Asks questions about objects, events and animals observed in their environment</li>
                                <li>Considers and offers explanations of how things might work</li>
                                <li>Shows interest in different animals and the sounds they make</li>
                                <li>Uses descriptive terms such as "fast" and "slow," "hot" and "cold"</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Creative arts & music</h4>
                            <ul>
                                <li>Begins to use a variety of art tools such as crayon, construction paper and colored pencils</li>
                                <li>Knows a few color words</li>
                                <li>Drawings have basic resemblance to objects and people</li>
                                <li>Articulates what he/she is drawing</li>
                            </ul>
                        </div>
                        <div>
                            <h4>Social studies</h4>
                            <ul>
                                <li>Recognizes common features of the home and neighborhood, such as trees, houses and streets</li>
                                <li>Shows interests in familiar people such as siblings, family members and friends</li>
                                <li>Shows interests in common jobs and professions such as firefighter, doctor and nurse</li>
                            </ul>
                        </div>
                    </div>

                    <div data-w-tab="Tab 3" class="w-tab-pane">
                        
                        <h3>Location</h3>
                        <div>Parkland Preschool <br>505 Parkvalley Road SE Calgary, <br>AB T2J 6M4CA<br>‍</div>
                        <a href="tel:4032250083">(403) 225-0083</a>
                        <div style="margin: 20px"><iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2515.3061223488044!2d-114.02853648402555!3d50.9180539795426!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x5371765822bd98f7%3A0x5a1b3ebdbb8ef939!2sParkland%20Preschool!5e0!3m2!1sen!2sca!4v1617982637444!5m2!1sen!2sca" width="900" height="450" style="border:0;" allowfullscreen="" loading="lazy"></iframe></div>
                        <h3>Hours</h3>
                        <div>
                            <ul style="list-style-type: none;">
                                <li>Mon. 09:00 a.m. - 11:30 a.m.</li>
                                <li>Tues. 09:00 a.m. - 11:30 a.m.</li>
                                <li>Wed. 09:00 a.m. - 11:30 a.m.</li>
                                <li>Thurs. 09:00 a.m. - 11:30 a.m.</li>
                                <li>Fri. 09:00 a.m. - 11:30 a.m.</li>
                                <li>Sat. Closed</li>
                                <li>Sun. Closed</li>
                            </ul>
                            
                            <p>If you would like to come and see us after hours, please drop us a line and we will try to accommodate your request.</p>
                        </div>
                        
                        <div class="w-form">
                        <h3>Contact us</h3>
                            <form action="/schoolinformation" method="post" id="wf-form-Contact-Form" name="wf-form-Contact-Form" data-name="Contact Form">
                                <div class="contact-form-grid">
                                    <div id="w-node-bdf7dca2-53cf-9fa3-6d5b-fd650b626ab3-9b89362a">
                                        <label for="First-Name" id="contact-first-name">First name *</label>
                                        <input type="text" class="w-input" maxlength="256" data-name="First Name" id="First-Name" value="${firstName}" name="firstName" required="">
                                    </div>
                                    <div id="w-node-bdf7dca2-53cf-9fa3-6d5b-fd650b626ab7-9b89362a">
                                        <label for="Last-Name" id="contact-last-name">Last name *</label>
                                        <input type="text" class="w-input" maxlength="256" id="Last-Name" value="${lastName}" name="lastName" required="">
                                    </div>
                                    <div id="w-node-bdf7dca2-53cf-9fa3-6d5b-fd650b626abb-9b89362a">
                                        <label for="Email" id="contact-email">Email *</label>
                                        <input type="email" class="w-input" maxlength="256" data-name="Email" id="Email" value="${email}" name="email" required="">
                                    </div>
                                    <div id="w-node-bdf7dca2-53cf-9fa3-6d5b-fd650b626abf-9b89362a">
                                        <label for="Contact-Phone-Number" id="contact-phone">Phone number</label>
                                        <input type="tel" class="w-input" maxlength="256" data-name="Contact Phone Number" value="${phoneNumber}" name="phoneNumber" id="Contact-Phone-Number">
                                    </div>
                                    <div id="w-node-bdf7dca2-53cf-9fa3-6d5b-fd650b626ac3-9b89362a">
                                        <label for="Message" id="contact-message">Message</label>
                                    </div>
                                </div><textarea data-name="Message" maxlength="5000" id="Message" class="w-input" name="message" required>${message}</textarea>
                                <input type="submit" value="Submit" data-wait="Please wait..." class="submit-button-10 w-button">
                            </form>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>

        <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=5fcbddeaf750a0c0eb0dba3c" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="js/webflow.js" type="text/javascript"></script>
        <!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
    </body>
</html>