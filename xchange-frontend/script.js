const USER_API = 'http://localhost:8080/api/users';
const SKILL_API = 'http://localhost:8080/api/skills';

async function fetchData() {
    try {
        const [uRes, sRes] = await Promise.all([fetch(USER_API), fetch(SKILL_API)]);
        const users = await uRes.json();
        const skills = await sRes.json();
        renderUI(users, skills);
    } catch (err) {
        console.error("Connection lost to Backend Port 8080");
    }
}

function renderUI(users, skills) {
    // 1. Render MySQL User Cards
    const grid = document.getElementById('user-grid');
    grid.innerHTML = users.map(user => {
        const userSkills = skills.filter(s => s.userId === user.id);
        const offer = userSkills.find(s => s.type === 'OFFER')?.skillName || '---';
        const want = userSkills.find(s => s.type === 'WANT')?.skillName || '---';

        return `
    <div class="user-card bg-white border border-slate-100 p-6 rounded-[1.5rem] shadow-sm relative overflow-hidden">
        <div class="absolute top-0 right-0 bg-indigo-100 text-indigo-600 text-[8px] font-black px-3 py-1 rounded-bl-xl uppercase tracking-widest">
            Node: ${user.id}
        </div>
        
        <div class="flex items-center space-x-4 mb-5">
            <div class="w-12 h-12 bg-indigo-600 rounded-2xl flex items-center justify-center text-white font-extrabold text-xl shadow-lg shadow-indigo-100">
                ${user.name.charAt(0)}
            </div>
            <div>
                <h4 class="font-bold text-slate-900">${user.name}</h4>
                <p class="text-[10px] text-slate-400 font-medium">Verified Community Member</p>
            </div>
        </div>

        <div class="grid grid-cols-2 gap-2 mb-5">
            <div class="bg-emerald-50 p-2 rounded-lg text-center border border-emerald-100">
                <p class="text-[9px] font-bold text-emerald-600 uppercase tracking-tighter">Offers</p>
                <p class="text-sm font-bold text-emerald-900">${offer}</p>
            </div>
            <div class="bg-orange-50 p-2 rounded-lg text-center border border-orange-100">
                <p class="text-[9px] font-bold text-orange-600 uppercase tracking-tighter">Needs</p>
                <p class="text-sm font-bold text-orange-900">${want}</p>
            </div>
        </div>

        <button onclick="requestTrade(${user.id})" class="w-full py-2.5 bg-indigo-50 hover:bg-indigo-600 hover:text-white rounded-xl text-xs font-bold transition-all text-indigo-600 border border-indigo-100 hover:border-indigo-600">
            Compute Match
        </button>
    </div>
`;
    }).join('');

    // 2. Render MongoDB Insights
    const stats = document.getElementById('skill-stats');
    stats.innerHTML = skills.map(skill => `
        <div class="bg-indigo-900/40 p-4 rounded-2xl border border-indigo-800/50">
            <div class="flex justify-between items-center mb-1">
                <span class="text-[9px] font-black tracking-widest ${skill.type === 'OFFER' ? 'text-emerald-400' : 'text-orange-400'}">${skill.type}</span>
                <span class="text-[8px] text-indigo-500 font-mono italic">${skill.id.substring(18)}</span>
            </div>
            <h5 class="text-white font-bold text-lg">${skill.skillName}</h5>
        </div>
    `).join('');
}

// Modal & POST Logic
function toggleModal() {
    document.getElementById('postModal').classList.toggle('hidden');
}

// Function to Save to BOTH MySQL and MongoDB
async function saveUserAndSkills() {
    const name = document.getElementById('newUserName').value;
    const email = document.getElementById('newUserEmail').value;
    const offer = document.getElementById('offerSkill').value;
    const want = document.getElementById('wantSkill').value;

    if(!name || !offer) return alert("Please fill Name and Offer!");

    try {
        // 1. Save User to MySQL
        const userRes = await fetch(USER_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, email, credits: 100 })
        });
        const savedUser = await userRes.json();

        // 2. Save Offer to MongoDB
        await fetch(SKILL_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId: savedUser.id, skillName: offer, type: 'OFFER' })
        });

        // 3. Save Want to MongoDB
        if(want) {
            await fetch(SKILL_API, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ userId: savedUser.id, skillName: want, type: 'WANT' })
            });
        }

        alert("Handshake Success: MySQL & MongoDB Updated!");
        toggleModal();
        fetchData();
    } catch (err) {
        alert("Error saving data");
    }
}

// DELETE function (Bonus for your Resume)
async function deleteUser(id) {
    if(confirm("Delete this user from MySQL?")) {
        await fetch(`${USER_API}/${id}`, { method: 'DELETE' });
        fetchData();
    }
}

function requestTrade(id) {
    alert("Algorithm: Finding optimal 3-way swap for User ID " + id);
}

fetchData();